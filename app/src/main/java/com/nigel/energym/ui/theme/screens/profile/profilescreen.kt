package com.nigel.energym.ui.theme.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun ProfileSCreen(navController: NavHostController) {
    val userData by viewModel.userData.collectAsState()
    val quote by viewModel.quote.collectAsState()

    userData?.let { user ->
        Profilescreen(
            userName = user.name,
            profilePicUrl = user.profilePicUrl,
            height = user.height,
            weight = user.weight,
            quote = quote,
            onFetchQuote = { viewModel.fetchRandomQuote() }
        )
    } ?: run {
        // Show loading or fallback UI
        Box(modifier=Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

}

@Preview
@Composable
private fun Profilepage() {
    ProfileSCreen(rememberNavController())

}