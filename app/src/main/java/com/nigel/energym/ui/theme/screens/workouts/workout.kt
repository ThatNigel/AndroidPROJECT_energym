package com.nigel.energym.ui.theme.screens.workouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun WorkoutScreen(navController: NavHostController,  workoutViewModel: WorkoutViewModel= viewModel(),
                  timerViewModel: WorkoutTimerViewModel = viewModel()) {
    val workoutList by workoutViewModel.workouts.collectAsState()
    val timeLeft by timerViewModel.timeLeft.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Workout") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                workoutViewModel.addWorkout("Pushups", 60)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Workout")
            }
        }
    ) { padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Workouts:", style = MaterialTheme.typography.titleMedium)
            workoutList.forEach { workout ->
                Text("${workout.name} - ${workout.duration}s")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Timer: ${timeLeft / 60}:${(timeLeft % 60).toString().padStart(2, '0')}",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Row (
                modifier = Modifier.padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button (onClick = { timerViewModel.startTimer(300) }) {
                    Text("Start")
                }
                Button(onClick = { timerViewModel.stopTimer() }) {
                    Text("Stop")
                }
                Button(onClick = { timerViewModel.resetTimer() }) {
                    Text("Reset")
                }
            }
        }
    }

}

@Preview
@Composable
private fun Workoutpage() {
    WorkoutScreen(rememberNavController())

}