package com.nigel.energym.ui.theme.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nigel.energym.R
import com.nigel.energym.navigation.ROUTE_LOGIN
import com.nigel.energym.navigation.ROUTE_REGISTER
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavHostController) {




        LaunchedEffect(Unit) {
            delay(1000)
            navController.navigate(ROUTE_REGISTER)
        }


        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        )
        {
            Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
                Image(
                    painter = painterResource(id = R.drawable.dumbell),
                    contentDescription = "Splash Screen logo",
                    modifier = Modifier.size(150.dp)
                )
                Text(
                    text = " WELCOME TO ENERGYM", color = Color.Black,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
            }

        }


}


@Preview
@Composable
private fun Splashpage() {
    SplashScreen(rememberNavController())

}