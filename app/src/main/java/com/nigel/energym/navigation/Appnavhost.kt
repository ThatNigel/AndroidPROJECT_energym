package com.nigel.energym.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nigel.energym.ui.theme.screens.home.HomeScreen
import com.nigel.energym.ui.theme.screens.login.LoginScreen
import com.nigel.energym.ui.theme.screens.profile.ProfileSCreen
import com.nigel.energym.ui.theme.screens.settings.SettingsScreen
import com.nigel.energym.ui.theme.screens.splash.SplashScreen
import com.nigel.energym.ui.theme.screens.workouts.WorkoutScreen

@Composable
fun AppNavHost(modifier: Modifier = Modifier,navController: NavHostController= rememberNavController(),startDestination:String= ROUTE_SPLASH) {
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
            ProfileSCreen(navController)
        }
        composable(ROUTE_SETTINGS){
            SettingsScreen(navController)
        }
        composable(ROUTE_WORKOUT){
            WorkoutScreen(navController)
        }



    }
}
