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
import com.nigel.energym.R
import com.nigel.energym.navigation.ROUTE_WORKOUT


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen( userName: String,navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
//            title ={"Home"} ,
                title = { Text("Hey, $userName") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.Green
                )
            )
        }
    )
    { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { (navController.navigate(ROUTE_WORKOUT)) },  // Navigate when clicked
//                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // Background Image
                    Image(
                        painter = painterResource(id = R.drawable.upperbody),
                        contentDescription = "Background Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop // Crop the image to fill the card
                    )
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Upper Body Workouts ",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "There are various workouts for the upper body " +
                                    "depending on the area you want to focus on . " +
                                    "It is broken down into Arms(Biceps,Triceps,Forearms),Shoulder and Back," +
                                    "Chest,Abs", style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }



            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { (navController.navigate(ROUTE_WORKOUT)) },  // Navigate when clicked
//                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // Background Image
                    Image(
                        painter = painterResource(id = R.drawable.lowerbody),
                        contentDescription = "Background Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop // Crop the image to fill the card
                    )
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Lower Body Workouts ",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "There are various workouts for the lower body " +
                                    "depending on the area you want to focus on . " +
                                    "It is broken down into Quads,Glutes,Hamstring and Calves", style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { (navController.navigate(ROUTE_WORKOUT)) },  // Navigate when clicked
//                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Background Image
                Image(
                    painter = painterResource(id = R.drawable.cycling),
                    contentDescription = "Background Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop // Crop the image to fill the card
                )
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Cycling",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Cycling  works all major muscle groups, especially in your legs," +
                                " while also engaging your core and arms for stability. " +
                                "Regular cycling increases stamina and overall fitness.It's gentle on your joints, " +
                                "making it suitable for people of all ages and fitness levels, " +
                                "including those with arthritis or" +
                                " joint issues.", style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

    }
}

















































@Preview
@Composable
private fun Homepage() {
    HomeScreen(userName = String(),rememberNavController())

}