package com.nigel.energym.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WorkoutTimerViewModel : ViewModel() {

    private val _timeLeft = MutableStateFlow(0) // in seconds
    val timeLeft: StateFlow<Int> = _timeLeft

    private var timerJob: Job? = null

    fun startTimer(durationInSeconds: Int) {
        timerJob?.cancel()
        _timeLeft.value = durationInSeconds

        timerJob = viewModelScope.launch {
            while (_timeLeft.value > 0) {
                delay(1000L)
                _timeLeft.value -= 1
            }
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
    }

    fun resetTimer() {
        _timeLeft.value = 0
        timerJob?.cancel()
    }
}
class WorkoutViewModel : ViewModel() {
    private val _workouts = MutableStateFlow<List<Workout>>(emptyList())
    val workouts: StateFlow<List<Workout>> = _workouts

    fun addWorkout(name: String, duration: Int) {
        _workouts.value = _workouts.value + Workout(name, duration)
    }
}
