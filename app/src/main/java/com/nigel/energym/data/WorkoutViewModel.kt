package com.nigel.energym.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.nigel.energym.Model.Exercise
import com.nigel.energym.Model.WorkoutCategory
import androidx.compose.runtime.State

class WorkoutViewModel : ViewModel(){
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    private val _categories = mutableStateListOf<WorkoutCategory>()
    val categories: List<WorkoutCategory> = _categories

    private val _selectedCategoryIndex = mutableStateOf(0)
    val selectedCategoryIndex: State<Int> = _selectedCategoryIndex

    init{
        loadExercisesFromFirebase()
    }


    init{
        _categories.addAll(
            listOf(
            WorkoutCategory("CrossFit", mutableListOf(
                Exercise("Warm-up"),
                Exercise("Push-Ups"),
                Exercise("Lunges"),
                Exercise("Burpees"),
                Exercise("Pull-ups"),
                Exercise("Kettlebell Swings"),
                Exercise("Box Jumps")
            )),
            WorkoutCategory("Lose Belly Fat", mutableListOf(
                Exercise("Plank"),
                Exercise("Leg Raises"),
                Exercise("Mountain Climbers"),
                Exercise("Russian Twists"),
                Exercise("Burpees"),
                Exercise("Star Jumps")
            )),
            WorkoutCategory("Cycling", mutableListOf(
                Exercise("Warm-up Ride"),
                Exercise("Hill Climb"),
                Exercise("Sprints"),
                Exercise("Cool Down")
            )),
            WorkoutCategory("Legs", mutableListOf(
                Exercise("Squats"),
                Exercise("Lunges"),
                Exercise("Leg Press"),
                Exercise("Sumo Squat"),
                Exercise("Calf Raises")
            ))

            )
        )
    }
    fun selectCategory(index:Int){
        _selectedCategoryIndex.value = index
    }
    fun deleteExercise(categoryIndex: Int, exercise: Exercise) {
        _categories[categoryIndex].exercises.remove(exercise)
        updateCategoryInFirebase(categoryIndex)
    }

    fun updateExercise(categoryIndex: Int, oldExercise: Exercise, newName: String) {
        val exercises = _categories[categoryIndex].exercises
        val index = exercises.indexOf(oldExercise)
        if (index >= 0) {
            exercises[index] = Exercise(newName, oldExercise.userId)
            updateCategoryInFirebase(categoryIndex)
        }
    }
    private fun updateCategoryInFirebase(categoryIndex: Int) {
        val category = _categories[categoryIndex]
        db.collection("workoutCategories")
            .document(category.name)
            .set(
                mapOf("exercises" to category.exercises.map {
                    mapOf("name" to it.name, "userId" to it.userId)
                })
            )
    }
    private fun loadExercisesFromFirebase() {
        db.collection("workoutCategories")
            .get()
            .addOnSuccessListener { result ->
                _categories.clear()
                result.forEach { doc ->
                    val name = doc.id
                    val exercises = (doc["exercises"] as? List<HashMap<String, String>>)?.map {
                        Exercise(it["name"] ?: "", it["userId"] ?: "")
                    }?.toMutableList() ?: mutableListOf()
                    _categories.add(WorkoutCategory(name, exercises))
                }
            }
    }


    fun addExercise(name:String){
//        _categories[_selectedCategoryIndex.value].exercises.add(Exercise(name))
        val currentCategory = _categories[_selectedCategoryIndex.value]
        val exercise = Exercise(name, auth.currentUser?.uid ?: "anonymous")
        currentCategory.exercises.add(exercise)

        // Save to Firebase
        db.collection("workoutCategories")
            .document(currentCategory.name)
            .update("exercises", currentCategory.exercises.map {
                mapOf("name" to it.name, "userId" to it.userId)
            })
            .addOnFailureListener {
                // Create document if not exists
                db.collection("workoutCategories")
                    .document(currentCategory.name)
                    .set(
                        mapOf("exercises" to currentCategory.exercises.map {
                            mapOf("name" to it.name, "userId" to it.userId)
                        })
                    )
            }
    }
    }

open class Screen(val route: String){
    object Workout : Screen("workout")
    object ExerciseDetail : Screen("categories[selectedIndex].exercises"){
        fun createRoute(categoryIndex: Int) = "exerciseDetail/$categoryIndex"
    }
}