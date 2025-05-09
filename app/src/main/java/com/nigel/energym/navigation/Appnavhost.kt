package com.nigel.energym.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nigel.energym.ui.theme.screens.home.HomeScreen
import com.nigel.energym.ui.theme.screens.login.LoginScreen
import com.nigel.energym.ui.theme.screens.profile.Profilescreen
import com.nigel.energym.ui.theme.screens.splash.SplashScreen
import com.nigel.energym.ui.theme.screens.workouts.ExerciseDetailScreen
import com.nigel.energym.ui.theme.screens.workouts.Lowerscreen
import com.nigel.energym.ui.theme.screens.workouts.UpperBodyscreen
import com.nigel.energym.ui.theme.screens.workouts.WorkoutScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_SPLASH,
    categoryIndex: Int
) {
    NavHost(navController = navController, modifier = modifier, startDestination = startDestination) {
        composable(ROUTE_SPLASH) {
            SplashScreen(navController)
        }
        composable ( ROUTE_LOGIN ){
            LoginScreen(navController)
        }
        composable(ROUTE_HOME){
            HomeScreen(userName = String(),navController)
        }
        composable(ROUTE_PROFILE){
            Profilescreen(navController)
        }
        composable(ROUTE_WORKOUTDETAILS){
            ExerciseDetailScreen(categoryIndex, viewModel(),navController)
        }
        composable(ROUTE_WORKOUT){
            WorkoutScreen(viewModel(),onCategoryClick = {
                navController.navigate(ROUTE_WORKOUTDETAILS)
            },navController)
        }
        composable(ROUTE_UPPER){
            UpperBodyscreen(navController)

        }
        composable(ROUTE_LOWER){
            Lowerscreen(navController)
        }




    }
}
