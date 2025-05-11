package com.nigel.energym.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UserProfileData(
    val id:String ="",
    val name:String="",
    val photoUrl:String="",
    val height:Int=0,
    val weight:Float=0f,
    val age:Int=0,
    val goal:String="",
    val activityLevel:String="",
    val workoutsCompleted: Int = 0,
    val caloriesBurned: Int = 0,
    val minutesExercised: Int = 0,
    val streakDays: Int = 0
)
sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(val userData: UserProfileData) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            try {
                // In a real app, you would fetch this data from a repository
                val userData = UserProfileData(
                    id = "user123",
                    name = "Alex Johnson",
                    photoUrl = "https://www.pexels.com/photo/person-holding-camera-1704488/",
                    height = 175,
                    weight = 70.5f,
                    age = 28,
                    goal = "Build Muscle",
                    activityLevel = "Intermediate",
                    workoutsCompleted = 47,
                    caloriesBurned = 12450,
                    minutesExercised = 1380,
                    streakDays = 15
                )
                _uiState.value = ProfileUiState.Success(userData)
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error("Failed to load profile: ${e.message}")
            }
        }
    }

    fun updateWeight(newWeight: Float) {
        val currentState = _uiState.value
        if (currentState is ProfileUiState.Success) {
            _uiState.value = ProfileUiState.Success(
                currentState.userData.copy(weight = newWeight)
            )
            // In a real app, you would also save this to a data source
        }
    }
    fun updateHeight(newHeight: Int) {
        val currentState = _uiState.value
        if (currentState is ProfileUiState.Success) {
            _uiState.value = ProfileUiState.Success(
                currentState.userData.copy(height = newHeight)
            )
            // In a real app, you would also save this to a data source
        }
    }
    fun updateAge(newAge: Int) {
        val currentState = _uiState.value
        if (currentState is ProfileUiState.Success) {
            _uiState.value = ProfileUiState.Success(
                currentState.userData.copy(age = newAge)
            )
            // In a real app, you would also save this to a data source
        }
    }

    fun updateGoal(newGoal: String) {
        val currentState = _uiState.value
        if (currentState is ProfileUiState.Success) {
            _uiState.value = ProfileUiState.Success(
                currentState.userData.copy(goal = newGoal)
            )
            // In a real app, you would also save this to a data source
        }
    }

    fun updateActivityLevel(newLevel: String) {
        val currentState = _uiState.value
        if (currentState is ProfileUiState.Success) {
            _uiState.value = ProfileUiState.Success(
                currentState.userData.copy(activityLevel = newLevel)
            )
            // In a real app, you would also save this to a data source
        }
    }
}