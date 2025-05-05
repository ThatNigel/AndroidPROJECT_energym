package com.nigel.energym.ui.theme.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nigel.energym.data.ProfileViewModel

@Composable
fun Profilescreen(navController: NavHostController) {
    val viewModel: ProfileViewModel = viewModel()
    val userData = viewModel.userData.collectAsState().value
    val quote = viewModel.quote.collectAsState().value

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

@Composable
fun Profilescreen(
    userName: String,
    profilePicUrl: String?,
    height: Int,
    weight: Int,
    quote: String?,
    onFetchQuote: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        AsyncImage(model = profilePicUrl, contentDescription = "Profile Picture")
        Text(text = userName, style = MaterialTheme.typography.headlineLarge)
        Text(text = "Height: $height cm")
        Text(text = "Weight: $weight kg")
        Text(text = "Quote: ${quote ?: "No quote available"}")
        Button(onClick = onFetchQuote) {
            Text("Fetch New Quote")
        }
    }

}

@Preview
@Composable
private fun Profilepage() {
    Profilescreen(rememberNavController())

}