package com.nigel.energym.data

import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _userData = MutableStateFlow<UserProfile?>(null)
    val userData: StateFlow<UserProfile?> = _userData

    private val _quote = MutableStateFlow("Tap the button for motivation!")
    val quote: StateFlow<String> = _quote

    init {
        loadUserData()
    }

    @OptIn(UnstableApi::class)
    fun loadUserData() {
        val uid = auth.currentUser?.uid ?: return
        db.collection("users").document(uid).get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    _userData.value = UserProfile(
                        name = doc.getString("name") ?: "Unknown",
                        height = doc.getLong("height")?.toInt() ?: 0,
                        weight = doc.getLong("weight")?.toInt() ?: 0,
                        profilePicUrl = doc.getString("profilePictureUrl") ?: ""
                    )
                }
            }
            .addOnFailureListener {
                Log.e("ProfileViewModel", "Error loading user data", it)
            }
    }

    fun fetchRandomQuote() {
        db.collection("quotes").get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    val quotes = result.map { it.getString("text") ?: "" }
                    _quote.value = quotes.random()
                } else {
                    _quote.value = "No quotes available."
                }
            }
            .addOnFailureListener {
                _quote.value = "Failed to load quotes."
            }
    }
}

data class UserProfile(
    val name: String,
    val height: Int,
    val weight: Int,
    val profilePicUrl: String,
)
