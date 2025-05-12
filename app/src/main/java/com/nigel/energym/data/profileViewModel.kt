package com.nigel.energym.data


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import java.io.ByteArrayOutputStream

data class UserProfileData(
    val id: String = "",
    val name: String = "",
    val photoUrl: String = "",
    val height: Int = 0,
    val weight: Float = 0f,
    val age: Int = 0,
    val goal: String = "",
    val activityLevel: String = "",
    val workoutsCompleted: Int = 0,
    val caloriesBurned: Int = 0,
    val minutesExercised: Int = 0,
    val streakDays: Int = 0,
    val email: String = ""
)

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(val userData: UserProfileData) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}

class ProfileViewModel(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadUserProfile()
    }

    fun loadUserProfile() {
        viewModelScope.launch {
            try {
                val currentUser = auth.currentUser
                    ?: throw Exception("No authenticated user found")

                val userSnapshot = getUserData(currentUser.uid)

                val userData = UserProfileData(
                    id = currentUser.uid,
                    name = userSnapshot.child("name").getValue(String::class.java) ?: currentUser.displayName ?: "",
                    email = currentUser.email ?: "",
                    photoUrl = currentUser.photoUrl?.toString() ?: "",
                    height = userSnapshot.child("height").getValue(Int::class.java) ?: 0,
                    weight = userSnapshot.child("weight").getValue(Float::class.java) ?: 0f,
                    age = userSnapshot.child("age").getValue(Int::class.java) ?: 0,
                    goal = userSnapshot.child("goal").getValue(String::class.java) ?: "",
                    activityLevel = userSnapshot.child("activityLevel").getValue(String::class.java) ?: ""
                )

                _uiState.value = ProfileUiState.Success(userData)
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error("Failed to load profile: ${e.message}")
            }
        }
    }

    // Helper function to get user data from Firebase Realtime Database using coroutines
    private suspend fun getUserData(userId: String): DataSnapshot = suspendCoroutine { continuation ->
        val userRef = database.reference.child("users").child(userId)

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                continuation.resume(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                continuation.resumeWithException(Exception(error.message))
            }
        })
    }

    fun updateProfile(
        name: String? = null,
        height: Int? = null,
        weight: Float? = null,
        age: Int? = null,
        goal: String? = null,
        activityLevel: String? = null
    ) {
        viewModelScope.launch {
            try {
                val currentUser = auth.currentUser
                    ?: throw Exception("No authenticated user found")

                // Prepare update map with non-null values
                val updateMap = mutableMapOf<String, Any>()
                name?.let { updateMap["name"] = it }
                height?.let { updateMap["height"] = it }
                weight?.let { updateMap["weight"] = it }
                age?.let { updateMap["age"] = it }
                goal?.let { updateMap["goal"] = it }
                activityLevel?.let { updateMap["activityLevel"] = it }

                // Update Firebase Realtime Database
                val userRef = database.reference.child("users").child(currentUser.uid)

                // Update each field individually to maintain existing data
                updateMap.forEach { (key, value) ->
                    userRef.child(key).setValue(value).await()
                }

                // Reload profile to reflect changes
                loadUserProfile()
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error("Failed to update profile: ${e.message}")
            }
        }
    }

    fun updateProfilePhoto(bitmap: Uri) {
        viewModelScope.launch {
            try {
                val currentUser = auth.currentUser
                    ?: throw Exception("No authenticated user found")

                // Convert bitmap to Base64 string
//                val base64Image = bitmap.toBase64String()

                // Update user's profile photo in Firebase Realtime Database
                database.reference
                    .child("users")
                    .child(currentUser.uid)
                    .child("photoUrl")
//                    .setValue(base64Image)
//                    .await()

                // Reload profile to reflect changes
                loadUserProfile()
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error("Failed to update profile photo: ${e.message}")
            }
        }
    }

    // Extension function to convert Bitmap to Base64 string
    private fun Bitmap.toBase64String(): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        // Compress image to reduce size - adjust quality as needed
        this.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    // Extension function to convert Base64 string to Bitmap
//    private fun String.toBitmap(): Bitmap? {
//        return try {
//            val decodedBytes = Base64.decode(this, Base64.DEFAULT)
//            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
//        } catch (e: Exception) {
//            null
//        }
//    }

    // Helper function to reset error state
    fun clearErrorState() {
        if (_uiState.value is ProfileUiState.Error) {
            _uiState.value = ProfileUiState.Loading
            loadUserProfile()
        }
    }
}




//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//import android.net.Uri
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.storage.FirebaseStorage
////import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.tasks.await
//import kotlin.text.Typography.dagger
//
//
//data class UserProfileData(
//    val id: String = "",
//    val name: String = "",
//    val photoUrl: String = "",
//    val height: Int = 0,
//    val weight: Float = 0f,
//    val age: Int = 0,
//    val goal: String = "",
//    val activityLevel: String = "",
//    val workoutsCompleted: Int = 0,
//    val caloriesBurned: Int = 0,
//    val minutesExercised: Int = 0,
//    val streakDays: Int = 0,
//    val email: String
//)
//sealed class ProfileUiState {
//    object Loading : ProfileUiState()
//    data class Success(val userData: UserProfileData) : ProfileUiState()
//    data class Error(val message: String) : ProfileUiState()
//}
//
//class ProfileViewModel (
//    private val auth: FirebaseAuth,
//    private val firestore: FirebaseFirestore,
//    private val storage: FirebaseStorage
//) : ViewModel() {
//
//    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
//    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
//
//    init {
//        loadUserProfile()
//    }
//
//    fun loadUserProfile() {
//        viewModelScope.launch {
//            try {
//                val currentUser = auth.currentUser
//                    ?: throw Exception("No authenticated user found")
//
//                val documentSnapshot = firestore.collection("users")
//                    .document(currentUser.uid)
//                    .get()
//                    .await()
//
//                val userData = UserProfileData(
//                    id = currentUser.uid,
//                    name = documentSnapshot.getString("name") ?: currentUser.displayName ?: "",
//                    email = currentUser.email ?: "",
//                    photoUrl = currentUser.photoUrl?.toString() ?: "",
//                    height = documentSnapshot.getLong("height")?.toInt() ?: 0,
//                    weight = documentSnapshot.getDouble("weight")?.toFloat() ?: 0f,
//                    age = documentSnapshot.getLong("age")?.toInt() ?: 0,
//                    goal = documentSnapshot.getString("goal") ?: "",
//                    activityLevel = documentSnapshot.getString("activityLevel") ?: ""
//                )
//
//                _uiState.value = ProfileUiState.Success(userData)
//            } catch (e: Exception) {
//                _uiState.value = ProfileUiState.Error("Failed to load profile: ${e.message}")
//            }
//        }
//    }
//
//    fun updateProfile(
//        name: String? = null,
//        height: Int? = null,
//        weight: Float? = null,
//        age: Int? = null,
//        goal: String? = null,
//        activityLevel: String? = null
//    ) {
//        viewModelScope.launch {
//            try {
//                val currentUser = auth.currentUser
//                    ?: throw Exception("No authenticated user found")
//
//                // Prepare update map with non-null values
//                val updateMap = mutableMapOf<String, Any>()
//                name?.let { updateMap["name"] = it }
//                height?.let { updateMap["height"] = it }
//                weight?.let { updateMap["weight"] = it }
//                age?.let { updateMap["age"] = it }
//                goal?.let { updateMap["goal"] = it }
//                activityLevel?.let { updateMap["activityLevel"] = it }
//
//                // Update Firestore
//                firestore.collection("users")
//                    .document(currentUser.uid)
//                    .update(updateMap)
//                    .await()
//
//                // Reload profile to reflect changes
//                loadUserProfile()
//            } catch (e: Exception) {
//                _uiState.value = ProfileUiState.Error("Failed to update profile: ${e.message}")
//            }
//        }
//    }
//
//    fun updateProfilePhoto(photoUri: Uri) {
//        viewModelScope.launch {
//            try {
//                val currentUser = auth.currentUser
//                    ?: throw Exception("No authenticated user found")
//
//                // Upload photo to Firebase Storage
//                val photoRef = storage.reference
//                    .child("profile_photos")
//                    .child("${currentUser.uid}.jpg")
//
//                // Upload file
//                val uploadTask = photoRef.putFile(photoUri).await()
//
//                // Get download URL
//                val downloadUrl = photoRef.downloadUrl.await().toString()
//
//                // Update user's profile in Firestore
//                firestore.collection("users")
//                    .document(currentUser.uid)
//                    .update("photoUrl", downloadUrl)
//                    .await()
//
//                // Reload profile to reflect changes
//                loadUserProfile()
//            } catch (e: Exception) {
//                _uiState.value = ProfileUiState.Error("Failed to update profile photo: ${e.message}")
//            }
//        }
//    }
//
//    // Helper function to reset error state
//    fun clearErrorState() {
//        if (_uiState.value is ProfileUiState.Error) {
//            _uiState.value = ProfileUiState.Loading
//            loadUserProfile()
//        }
//    }
//}





//class ProfileViewModel : ViewModel() {
//
//    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
//    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
//
//    init {
//        loadUserProfile()
//    }
//
//    private fun loadUserProfile() {
//        viewModelScope.launch {
//            try {
//                // In a real app, you would fetch this data from a repository
//                val userData = UserProfileData(
//                    id = "user123",
//                    name = "Alex Johnson",
//                    photoUrl = "https://www.pexels.com/photo/person-holding-camera-1704488/",
//                    height = 175,
//                    weight = 70.5f,
//                    age = 28,
//                    goal = "Build Muscle",
//                    activityLevel = "Intermediate",
//                    workoutsCompleted = 47,
//                    caloriesBurned = 12450,
//                    minutesExercised = 1380,
//                    streakDays = 15
//                )
//                _uiState.value = ProfileUiState.Success(userData)
//            } catch (e: Exception) {
//                _uiState.value = ProfileUiState.Error("Failed to load profile: ${e.message}")
//            }
//        }
//    }
//
//    fun updateWeight(newWeight: Float) {
//        val currentState = _uiState.value
//        if (currentState is ProfileUiState.Success) {
//            _uiState.value = ProfileUiState.Success(
//                currentState.userData.copy(weight = newWeight)
//            )
//            // In a real app, you would also save this to a data source
//        }
//    }
//    fun updateHeight(newHeight: Int) {
//        val currentState = _uiState.value
//        if (currentState is ProfileUiState.Success) {
//            _uiState.value = ProfileUiState.Success(
//                currentState.userData.copy(height = newHeight)
//            )
//            // In a real app, you would also save this to a data source
//        }
//    }
//    fun updateAge(newAge: Int) {
//        val currentState = _uiState.value
//        if (currentState is ProfileUiState.Success) {
//            _uiState.value = ProfileUiState.Success(
//                currentState.userData.copy(age = newAge)
//            )
//            // In a real app, you would also save this to a data source
//        }
//    }
//
//    fun updateGoal(newGoal: String) {
//        val currentState = _uiState.value
//        if (currentState is ProfileUiState.Success) {
//            _uiState.value = ProfileUiState.Success(
//                currentState.userData.copy(goal = newGoal)
//            )
//            // In a real app, you would also save this to a data source
//        }
//    }
//
//    fun updateActivityLevel(newLevel: String) {
//        val currentState = _uiState.value
//        if (currentState is ProfileUiState.Success) {
//            _uiState.value = ProfileUiState.Success(
//                currentState.userData.copy(activityLevel = newLevel)
//            )
//            // In a real app, you would also save this to a data source
//        }
//    }
//}