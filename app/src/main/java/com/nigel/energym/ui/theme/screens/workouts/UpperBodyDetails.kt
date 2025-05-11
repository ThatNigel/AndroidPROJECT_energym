package com.nigel.energym.ui.theme.screens.workouts

import android.R.attr.fontWeight
import android.R.attr.letterSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource

import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nigel.energym.R



import com.nigel.energym.navigation.ROUTE_PROFILE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpperBodyscreen(navController: NavHostController) {
    val light_blue = colorResource(id = R.color.light_blue)
    val Navy_blue = colorResource(id= R.color.Navy_Blue)
    val Blue_Grotto = colorResource(id = R.color.Blue_Grotto)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Upper Body Workouts",
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
                            .height(400.dp).background(color = Navy_blue)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "ARMS", textDecoration = TextDecoration.Underline,
//                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                            Text("There are Various ways to workout the Arms .")
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "1.Beginner",
                                textDecoration = TextDecoration.Underline,
//                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("a. Side arm raises :Do this for 30 second(Use a stopwatch)\nb.Tricep dips: 5 sets each 10 reps\nc.Arm circles clockwise and anticlockwise :each for 30 second\nd. Diamond push ups :PLACE YOUR HANDS BELOW YOUR CHEST:3 sets each 10 reps\ne.Push ups :3 sets each 10 reps\nf.Forearm curls using 5kg weights:4 sets each 10 reps\ng.Jumping jacks :30 second timer")
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "2.Intermediate",
                                textDecoration = TextDecoration.Underline,
//                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("a.Arm circles clockwise abd anti clockwise :each for 30 second\nb.Floor tricep dips:5 sets each 10 reps\nc.Push  ups:5 sets each 20 reps\nd.Push up and rotation:4 sets each 5 reps\ne.Burpees:3 sets each 10 reps\nf.Tricep stretches :do for 30 seconds\ng.Rope skipping :10 sets each 40 oscillation\nh.Push up and hold:3 sets each 10 reps\ni.Archer push ups :2 sets each 15 reps\nj.Diamond push ups :2 sets each 15 reps")
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "3.Advanced",
                                textDecoration = TextDecoration.Underline,
//                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("a.Stagger push ups: 3 sets each 10 reps\nb.Hindu push ups :2 sets each 10 reps")
                        }

                    }
                }
            }
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
                                "CHEST", textDecoration = TextDecoration.Underline,
//                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                            Text("There are Various ways to workout the Chest.")
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "1.Beginner",
                                textDecoration = TextDecoration.Underline,
//                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("a.Incline push ups :4 sets each 10 reps\nb.knee push ups: 3 sets each 15 reps\nc.Push ups:3 sets each 10 reps\nd.Wide arm push ups:2 sets each 15 reps\ne.Decline push ups:3 sets each 10 reps\nf.Hindu push ups:2 sets each 15 reps\ng.Cobra stretch:30 seconds")
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "2.Intermediate",
                                textDecoration = TextDecoration.Underline,
//                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("a.Jumping jacks:30 second\nb.Knee push ups :3 sets each 10 reps\nc.Staggered push ups:2 sets each 15 reps\nd.Push ups and rotation:2 sets each 10 reps\ne.Decline push up :3 sets each 10 reps\nf.Box push ups :3 sets each 16 reps\ng.Wide arm push ups:2 sets each 15 reps\nh.Cobra stretch:30 seconds")
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "3.Advanced",
                                textDecoration = TextDecoration.Underline,)
//                                fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("a.Incline push ups :4 sets each 10 reps\nb.Decline push ups :3 sets each 15 reps\nc.Burpees:3 sets each 15 reps\nd.Spiderman push ups 2 sets each 15 reps\ne.Arm Circles:one minute\nf.Cobra stretch:30 seconds")




                        }

                    }
                }

            }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(400.dp).background(Color.Cyan)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "SHOULDER & BACK", textDecoration = TextDecoration.Underline,
//                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                            Text("There are Various ways to workout the shoulder and back .")
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "1.Beginner",
                                textDecoration = TextDecoration.Underline,)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("a.Hyperextension:2 sets each 7 reps\nb.Pike push up:2 sets each 10 reps\nc.Inchworms:2 sets each 8 reps\nd.Supine push up :3 sets each 10 reps\ne.Child pose:30 seconds")
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "1.Intermediate",
                                textDecoration = TextDecoration.Underline,)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("a.Pike push ups:3 sets each 15 reps\nb.Rhomboid pulls:2 sets each 15 reps\nc.Floor tricep dips :3 sets each 10 reps\nd.Hover push up:2 sets each 10 reps")
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "1.Advanced",
                                textDecoration = TextDecoration.Underline,)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("a.Reverse push up :3 sets each 14 reps\nb.Side lying floor stretch left and right :30 second each\nc.Floor y raises:3 sets each 15 reps\nd.Pike push ups:3 sets each 10 reps\ne.Child pose:30 seconds")

                        }

                    }

                }
            }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(400.dp).background(Color.Cyan)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "ABS", textDecoration = TextDecoration.Underline,
//                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                            Text("There are Various ways to workout the abdominals.")
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "1.Beginner",
                                textDecoration = TextDecoration.Underline,)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("a.Sit ups:2 sets each 15 reps\nb.Abdominal Crunches:2 sets each 20 reps\nc.Russian twists:2 sets each 15 reps\nd.Plank :45 seconds\ne.Cobra stretch:30 seconds\nf.Mountain climber:3 steps each 10 reps")
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "2.Intermediate",
                                textDecoration = TextDecoration.Underline,)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("a.Heel Touch :3 sets each 15 reps\nb.Cross over crunch :2 sets each 15 reps\nc.Butt bridge:2 sets each 10 reps\nd.Side bridge :3 sets each 10 reps for both sides\ne.Plank:45 seconds\nf.Bicycle crunch:3 sets 15 reps")
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "3.Advanced",
                                textDecoration = TextDecoration.Underline,)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("a.V ups:3 sets each 20 reps\nb.Cross over crunch :2 sets each 15 reps\nc.Plank:50 seconds\nd.Russian twist:3 sets each 15 reps \ne.Heel touch:3 sets 15 reps\nf.Cobra stretch:45 seconds")

                        }

                    }

                }
            }

        }

    }
}

@Preview
@Composable
private fun UpperDetails() {
    UpperBodyscreen(rememberNavController())
    
}