package com.nigel.energym.navigation

import android.window.SplashScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    LaunchedEffect(Unit) {
        delay(2000)
    }
    Box(contentAlignment = Alignment.Center){}
}