package com.nigel.energym.ui.theme.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nigel.energym.data.AuthViewModel
import com.nigel.energym.navigation.ROUTE_HOME
import com.nigel.energym.navigation.ROUTE_PROFILE
import com.nigel.energym.navigation.ROUTE_WORKOUT

@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var confirmpassword by remember { mutableStateOf(TextFieldValue("")) }
    var userName by remember { mutableStateOf(TextFieldValue("")) }


    var context = LocalContext.current

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.background(Color.Cyan).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Login ",
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline,
                fontFamily = FontFamily.Cursive,
                fontSize = 30.sp,
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            )
//            EMAIL INPUT
            TextField(
                value = email, onValueChange = { email = it },
                label = { Text(text = "Enter Email") },
                leadingIcon = { Icon(Icons.Default.Email , contentDescription = "Email")},
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( vertical=8.dp),
                shape = RoundedCornerShape(60.dp),
            )
//            USERNAMEINPUT
            TextField( value = userName,
                onValueChange = {userName=it},
                label = { Text("Username ") },
                leadingIcon = { Icon(Icons.Default.AccountCircle , contentDescription = "User")},
                placeholder = { Text("Enter your Username") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( vertical = 8.dp),
                shape = RoundedCornerShape(60.dp)

            )





//            PASSWORD INPUT
            TextField(value =password , onValueChange = {password=it},
            label = { Text(text = "Enter Password")
                    FontFamily.Monospace},
                leadingIcon = {Icon(Icons.Default.Lock , contentDescription = "Password")},
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical =  8.dp),
            shape = RoundedCornerShape(60.dp),
                )

            TextField(
                value = confirmpassword, onValueChange = { confirmpassword = it },
                label = { Text(" Confirm Password")
                    FontFamily.Monospace},
                leadingIcon = {Icon(Icons.Default.Lock , contentDescription = "Password")},
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical =  8.dp),
                shape = RoundedCornerShape(60.dp),

            )


//            LOGINBUTTON
            Button(onClick = {
                navController.navigate(ROUTE_HOME){
                    val mylogin= AuthViewModel(navController, context )
                    mylogin.login(email.text.trim(),password.text.trim(),confirmpassword.text.trim())

                }
            }, modifier = Modifier
                .width(100.dp)
                .height(50.dp)) {
                Text("Log in")
            }


        }
    }
}

@Preview
@Composable
private fun Loginpage() {
    LoginScreen( rememberNavController())

}




