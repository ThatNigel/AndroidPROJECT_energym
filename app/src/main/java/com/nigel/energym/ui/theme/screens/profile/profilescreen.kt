package com.nigel.energym.ui.theme.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nigel.energym.R
import androidx.compose.ui.res.colorResource
import com.nigel.energym.data.ProfileViewModel

@Composable
fun Profilescreen(navController: NavHostController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val email = user?.email ?: "No Email"
    val light_blue = colorResource(id = R.color.light_blue)

    var weightInput by remember { mutableStateOf("") }
    var heightInput by remember { mutableStateOf("") }
    var bmiResult by remember { mutableStateOf("") }
    var exerciseSuggestion by remember { mutableStateOf("") }

    val database = FirebaseDatabase.getInstance().reference

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = light_blue) // Light blue background
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF01579B) // Dark blue
        )

        Text(
            text = "Email: $email",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )

        OutlinedTextField(
            value = weightInput,
            onValueChange = { weightInput = it },
            label = { Text("Weight (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = heightInput,
            onValueChange = { heightInput = it },
            label = { Text("Height (cm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val weight = weightInput.toFloatOrNull()
                val heightCm = heightInput.toFloatOrNull()
                if (weight != null && heightCm != null && heightCm > 0) {
                    val heightM = heightCm / 100
                    val bmi = weight / (heightM * heightM)
                    val bmiCategory = when {
                        bmi < 18.5 -> "Underweight"
                        bmi < 25 -> "Normal weight"
                        bmi < 30 -> "Overweight"
                        else -> "Obese"
                    }
                    bmiResult = "BMI: %.2f (%s)".format(bmi, bmiCategory)
                } else {
                    bmiResult = "Please enter valid weight and height."
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0288D1)), // Blue
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate BMI")
        }

        Text(
            text = bmiResult,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.DarkGray
        )

        OutlinedTextField(
            value = exerciseSuggestion,
            onValueChange = { exerciseSuggestion = it },
            label = { Text("Suggest a New Exercise") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val suggestion = exerciseSuggestion.trim()
                if (suggestion.isNotEmpty()) {
                    val suggestionRef = database.child("exerciseSuggestions").push()
                    val suggestionData = mapOf(
                        "email" to email,
                        "suggestion" to suggestion
                    )
                    suggestionRef.setValue(suggestionData)
                    exerciseSuggestion = ""
                    // Optionally, show a confirmation message
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0288D1)), // Blue
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit Suggestion")
        }
    }


//    val viewModel: ProfileViewModel = viewModel()
//    val userData = viewModel.userData.collectAsState().value
//    val quote = viewModel.quote.collectAsState().value
//    val context = LocalContext.current
//
//    userData?.let { user ->
//        Profilescreen(
//            userName = user.name,
//            profilePicUrl = user.profilePicUrl,
//            height = user.height,
//            weight = user.weight,
//            quote = quote,
//            onFetchQuote = { viewModel.fetchRandomQuote() }
//        )
//    } ?: run {
//        // Show loading or fallback UI
//        Box(modifier=Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            CircularProgressIndicator()
//        }
//    }
//
//}
//
//@Composable
//fun Profilescreen(
//    userName: String,
//    profilePicUrl: String?,
//    height: Int,
//    weight: Int,
//    quote: String?,
//    onFetchQuote: () -> Unit
//) {
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
////        AsyncImage(model = profilePicUrl, contentDescription = "Profile Picture")
//        Text(text = userName, style = MaterialTheme.typography.headlineLarge)
//        Text(text = "Height: $height cm")
//        Text(text = "Weight: $weight kg")
//        Text(text = "Quote: ${quote ?: "No quote available"}")
//        Button(onClick = onFetchQuote) {
//            Text("Fetch New Quote")
//        }
//    }
//
//}
}
@Preview
@Composable
private fun Profilepage() {
    Profilescreen(rememberNavController())

}