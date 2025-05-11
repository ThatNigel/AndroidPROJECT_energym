package com.nigel.energym.ui.theme.screens.workouts

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.R.attr.fontWeight
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import android.R.attr.letterSpacing
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.ui.res.colorResource
import com.nigel.energym.R
import android.R.attr.fontWeight


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Lowerscreen(navController: NavHostController){
    val light_blue = colorResource(id = R.color.light_blue)
    val Navy_blue = colorResource(id= R.color.Navy_Blue)
    val Blue_Grotto = colorResource(id = R.color.Blue_Grotto)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Lower Body Workouts",
                        fontSize = 30.sp,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.DarkGray
                )

            )
        }
    ) { paddingvalues ->
        LazyRow(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingvalues)
                .padding(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 8.dp)

                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(400.dp).background(color = Blue_Grotto)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "Legs", textDecoration = TextDecoration.Underline,
//                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                            Text("There are Various ways to workout the Lower Body .")
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "1.Beginner",
                                textDecoration = TextDecoration.Underline,
//                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("a.Side Hop:30 seconds\nb.Squats:3 sets each 20 reps\nc.Backward Lunge:2 sets each 15 reps\nd.Side lying leg lift :2 sets each 20 reps for each leg\ne.Donkey kicks :3 sets each 20 reps\nf.Calf raises:5 sets each 15 reps\ng.Sumo squat :3 sets each 15 reps")
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "2.Intermediate",
                                textDecoration = TextDecoration.Underline,
//                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("a.Jumping jacks:30 second\nb.Squats:3 sets each 20 reps\nc.Fire Hydrant:2 sets each 15 reps each leg\nd.Lunges:4 sets each 25 reps\ne.Sumo squat:3 sets each 20 reps\nf.Reverse Flutter Kicks:3 sets each 15 reps\ng.Calf raises:3 sets each 25 reps\nh.Calf stretch:30 seconds ")
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "3.Advanced",
                                textDecoration = TextDecoration.Underline,
//                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("a.Burpees:3 sets each 15 reps\nb.Squats:3 sets each 25 reps\nc.Bottom leg lift:lie side way and cross your legs :3 sets each 15 reps\nd.Curtsy Lunges:3 sets each 20 reps\ne.Jumping jacks:3 sets each 20 reps\nf.Glute kickback:3 sets each 15 reps\ng.Wall sit :45 seconds\nh.Single calf raise:4 sets each 20 reps (remember to alternate)\ni.Touch your toes:30 seconds(DON'T BEND YOUR KNEES)")
                        }

                    }
                }
            }

        }

    }

}

@Preview
@Composable
private fun Lowerpage() {
    Lowerscreen(rememberNavController())
}