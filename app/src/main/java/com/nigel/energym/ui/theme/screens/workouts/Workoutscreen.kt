package com.nigel.energym.ui.theme.screens.workouts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.nigel.energym.data.WorkoutViewModel
import com.nigel.energym.ui.theme.screens.workouts.WorkoutScreen

@Composable
fun WorkoutScreen(
    viewModel: WorkoutViewModel,onCategoryClick: (Int) -> Unit,navController: NavHostController
){
    val categories =viewModel.categories
    val selectedIndex by viewModel.selectedCategoryIndex

    Column(Modifier.fillMaxSize().padding(16.dp)){
        Text("Workout",style = MaterialTheme.typography.headlineLarge)

        LazyRow (modifier = Modifier.padding(vertical = 16.dp)){
            itemsIndexed(categories){index,category ->
                Card (
                    modifier = Modifier.padding(end = 8.dp).clickable { viewModel.selectCategory(index) },
//                    backgroundColor= Color.Cyan
                ){
                    Text(
                        text = category.name,
                        modifier = Modifier.padding(16.dp),
                        color = Color.White
                    )
                }
            }
        }
        Text("Exercises",style = MaterialTheme.typography.headlineLarge)

//        val exercises = categories[selectedIndex].exercises
//        val exercises = categories.getOrNull(viewModel.selectedCategoryIndex)?.exercises ?: emptyList()


//        LazyColumn (modifier = Modifier.weight(1f)){
//            items(exercises){exercise ->
//                Text(".${exercise.name}",modifier = Modifier.padding(8.dp))
//            }
//        }

        var newExercise by remember { mutableStateOf("")}

        Row (verticalAlignment = Alignment.CenterVertically ){
            TextField(
                value = newExercise,
                onValueChange = {newExercise = it},
                modifier = Modifier.weight(1f),
                placeholder = {"Add  new exercise"}
            )
            IconButton(onClick = {
                if(newExercise.isNotBlank()){viewModel.addExercise(newExercise)
                    newExercise = ""
                }
            }){
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    }
}

//@androidx.compose.ui.tooling.preview.Preview
//@androidx.compose.runtime.Composable
//private fun Workoutpage() {
//    val fakeViewModel = WorkoutViewModel() // Ideally mocked
//    WorkoutScreen(  viewModel = fakeViewModel,
//        onCategoryClick = {},
//        navController = NavHostController) // This may crash in preview, so use PreviewParameter if needed
//}