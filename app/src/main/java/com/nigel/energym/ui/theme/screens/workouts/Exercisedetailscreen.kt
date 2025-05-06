package com.nigel.energym.ui.theme.screens.workouts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nigel.energym.Model.Exercise
import com.nigel.energym.data.WorkoutViewModel

@Composable
fun ExerciseDetailScreen(categoryIndex: Int, viewModel: WorkoutViewModel) {
    val category = viewModel.categories[categoryIndex]
    var newExercise by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("${category.name} Exercises", style = MaterialTheme.typography.headlineLarge)

//        LazyColumn(modifier = Modifier.weight(1f)) {
//            items(category.exercises) { exercise ->
//                Text("• ${Exercise.name}", modifier = Modifier.padding(8.dp))
//            }
//        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = newExercise,
                onValueChange = { newExercise = it },
                placeholder = { Text("Add exercise") },
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {
                if (newExercise.isNotBlank()) {
                    viewModel.addExercise(newExercise)
                    newExercise = ""
                }
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    }
}
