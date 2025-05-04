package com.nigel.energym.ui.theme.screens.workouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.nigel.energym.data.WorkoutViewModel

@Composable
fun ExerciseDetailScreen(categoryIndex: Int, viewModel: WorkoutViewModel) {
    val category = viewModel.categories[categoryIndex]
    var newExercise by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("${category.name} Exercises", style = MaterialTheme.typography.h5)

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(category.exercises) { exercise ->
                Text("• ${exercise.name}", modifier = Modifier.padding(8.dp))
            }
        }

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
