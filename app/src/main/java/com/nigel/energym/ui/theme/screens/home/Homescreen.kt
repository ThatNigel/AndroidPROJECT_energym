package com.nigel.energym.ui.theme.screens.home

import android.R.attr.onClick
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nigel.energym.navigation.ROUTE_WORKOUT


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen( userName: String,navController: NavHostController) {
//    Scaffold(
//        topBar = {
//            TopAppBar(
////            title ={"Home"} ,
//                title = { Text("Hey, $userName") },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primary,
//                    titleContentColor = Color.Green
//                )
//            )
//        }
//    )
//    { paddingValues ->
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp)
//                    .clickable { (navController.navigate(ROUTE_WORKOUT)) },  // Navigate when clicked
////                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//            ) {
//                Box(modifier = Modifier.fillMaxSize()) {
//                    // Background Image
//                    Image(
//                        painter = painterResource(id = R.drawable.upperbody),
//                        contentDescription = "Background Image",
//                        modifier = Modifier.fillMaxSize(),
//                        contentScale = ContentScale.Crop // Crop the image to fill the card
//                    )
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        Text(
//                            text = "Upper Body Workouts ",
//                            style = MaterialTheme.typography.titleMedium
//                        )
//                        Spacer(modifier = Modifier.height(4.dp))
//                        Text(
//                            text = "There are various workouts for the upper body " +
//                                    "depending on the area you want to focus on . " +
//                                    "It is broken down into Arms(Biceps,Triceps,Forearms),Shoulder and Back," +
//                                    "Chest,Abs", style = MaterialTheme.typography.bodyMedium
//                        )
//                    }
//                }
//            }
//
//
//
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp)
//                    .clickable { (navController.navigate(ROUTE_WORKOUT)) },  // Navigate when clicked
////                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//            ) {
//                Box(modifier = Modifier.fillMaxSize()) {
//                    // Background Image
//                    Image(
//                        painter = painterResource(id = R.drawable.lowerbody),
//                        contentDescription = "Background Image",
//                        modifier = Modifier.fillMaxSize(),
//                        contentScale = ContentScale.Crop // Crop the image to fill the card
//                    )
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        Text(
//                            text = "Lower Body Workouts ",
//                            style = MaterialTheme.typography.titleMedium
//                        )
//                        Spacer(modifier = Modifier.height(4.dp))
//                        Text(
//                            text = "There are various workouts for the lower body " +
//                                    "depending on the area you want to focus on . " +
//                                    "It is broken down into Quads,Glutes,Hamstring and Calves", style = MaterialTheme.typography.bodyMedium
//                        )
//                    }
//                }
//            }
//
//        }
//
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp)
//                .clickable { (navController.navigate(ROUTE_WORKOUT)) },  // Navigate when clicked
////                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//        ) {
//            Box(modifier = Modifier.fillMaxSize()) {
//                // Background Image
//                Image(
//                    painter = painterResource(id = R.drawable.cycling),
//                    contentDescription = "Background Image",
//                    modifier = Modifier.fillMaxSize(),
//                    contentScale = ContentScale.Crop // Crop the image to fill the card
//                )
//                Column(modifier = Modifier.padding(16.dp)) {
//                    Text(
//                        text = "Cycling",
//                        style = MaterialTheme.typography.titleMedium
//                    )
//                    Spacer(modifier = Modifier.height(4.dp))
//                    Text(
//                        text = "Cycling  works all major muscle groups, especially in your legs," +
//                                " while also engaging your core and arms for stability. " +
//                                "Regular cycling increases stamina and overall fitness.It's gentle on your joints, " +
//                                "making it suitable for people of all ages and fitness levels, " +
//                                "including those with arthritis or" +
//                                " joint issues.", style = MaterialTheme.typography.bodyMedium
//                    )
//                }
//            }
//        }
//
//    }
//}
}


















@Preview
@Composable
private fun Homepage() {
    HomeScreen(userName = String(),rememberNavController())

}


@Preview(showSystemUi = true)
@Composable
fun DefaultPreview() {
    val workOutCategories = listOf("Full body", "Cardio", "Cross Fit", "Cyclist", "Glutes", "Power")
    StoreAppTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier.padding(horizontal = 18.dp),
                    elevation = 0.dp,
                    backgroundColor = Color.White,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(65.dp)
                                .clip(shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.profile),
                                contentDescription = " Profile picture",
                                contentScale = ContentScale.Fit
                            )
                        }

                        Text(buildAnnotatedString {
                            append("Hello, ")
                            withStyle(
                                style = SpanStyle(
                                    color = colorResource(id = R.color.black),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                            ) {
                                append("Geraud")
                            }
                        }, modifier = Modifier.padding(start = 10.dp))

                        Spacer(modifier = Modifier.weight(1f))

                        BadgedBox(modifier = Modifier.padding(end = 10.dp),
                            badge = {
                                Badge(
                                    Modifier
                                        .clip(CircleShape)
                                        .background(Color.Red)
                                        .align(Alignment.BottomEnd)
                                )
                            }) {
                            Icon(
                                imageVector = Icons.Filled.Notifications,
                                contentDescription = "notification icon",
                                tint = Color.Black
                            )
                        }
                    }
                }
            },
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier.clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
                    elevation = 5.dp,
                    backgroundColor = Color.Black,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {

                        BottomNavigationItem(
                            selectedContentColor = Color.White,
                            unselectedContentColor = Color.LightGray,
                            selected = true,
                            onClick = { /*TODO*/ },
                            icon = {
                                Icon(
                                    imageVector = Icons.Filled.Person,
                                    contentDescription = "icon",
                                    modifier = Modifier.size(30.dp)
                                )
                            },
                        )

                        BottomNavigationItem(selectedContentColor = Color.Black,
                            unselectedContentColor = Color.LightGray,
                            selected = false,
                            onClick = { /*TODO*/ },
                            icon = {
                                Icon(
                                    imageVector = Icons.Outlined.Fullscreen,
                                    contentDescription = "icon",
                                    modifier = Modifier.size(30.dp)
                                )
                            })

                        BottomNavigationItem(selectedContentColor = Color.Black,
                            unselectedContentColor = Color.LightGray,
                            selected = false,
                            onClick = { /*TODO*/ },
                            icon = {
                                Icon(
                                    imageVector = Icons.Outlined.Tune,
                                    contentDescription = "icon",
                                    modifier = Modifier.size(30.dp)
                                )
                            })

                        BottomNavigationItem(
                            selectedContentColor = Color.Black,
                            unselectedContentColor = Color.LightGray,
                            selected = false,
                            onClick = { /*TODO*/ },
                            icon = {
                                Icon(
                                    imageVector = Icons.Outlined.RadioButtonUnchecked,
                                    contentDescription = "icon",
                                    modifier = Modifier.size(30.dp)
                                )
                            })
                    }
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 18.dp)
            ) {

                Spacer(modifier = Modifier.size(15.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    items(workOutCategories) { workOut ->
                        WorkOutType(workOut)
                    }
                }

                Spacer(modifier = Modifier.size(30.dp))

                Box(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(10))
                        .background(color = colorResource(id = R.color.light_purple))
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 22.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Loose\nbelly fat", color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp
                            )

                            Button(
                                shape = RoundedCornerShape(20.dp),
                                contentPadding = PaddingValues(6.dp),
                                onClick = { /*TODO*/ },
                                colors = ButtonDefaults.textButtonColors(
                                    backgroundColor = colorResource(id = R.color.purple_200)
                                )
                            ) {
                                Text(text = "Middle level", color = Color.White, fontSize = 18.sp)
                            }
                        }

                        Spacer(modifier = Modifier.size(15.dp))

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(30.dp))
                                .background(Color.White)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Spacer(modifier = Modifier.weight(0.5f))
                                Image(
                                    painter = painterResource(id = R.drawable.dumbel),
                                    contentDescription = "Dummy weight image",
                                    modifier = Modifier.size(200.dp)
                                )
                            }

                        }

                        Spacer(modifier = Modifier.size(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Schedule,
                                contentDescription = "Timer",
                                tint = Color.Black
                            )

                            Text(
                                text = "40 minutes",
                                fontSize = 18.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(start = 5.dp)
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            TextButton(
                                onClick = { /*TODO*/ }, colors = ButtonDefaults.textButtonColors(
                                    backgroundColor = Color.Transparent
                                )
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                                    Text(
                                        text = "Start",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black,
                                    )

                                    Icon(
                                        imageVector = Icons.Filled.ArrowForward,
                                        contentDescription = "Timer",
                                        tint = Color.Black
                                    )
                                }
                            }
                        }

                    }
                }

                Spacer(modifier = Modifier.size(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(20))
                        .background(colorResource(id = R.color.orange))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Box(contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(75.dp),
                                color = Color.Black,
                                progress = 0.56f,
                                strokeWidth = 8.dp,
                            )

                            Text(
                                text = "56%",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp
                            )
                        }

                        Column {
                            Text(
                                text = "Great!",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Text(
                                text = "You've lost 70% of your \ndaily calorie intake",
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }
                    }
                }

            }
        }
    }
}

fun TopAppBar(modifier: Modifier, elevation: Dp, backgroundColor: Color, scrollBehavior: () -> Unit) {

}
