package com.nigel.energym.Model

data class Exercise (
        val name:String = "",
        val userId: String = ""
        )
data class WorkoutCategory(
    val name:String = "",
    val exercises:MutableList<Exercise>
)