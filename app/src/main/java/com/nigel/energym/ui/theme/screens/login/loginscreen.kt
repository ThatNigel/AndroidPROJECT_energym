package com.nigel.energym.ui.theme.screens.login


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nigel.energym.R
import com.nigel.energym.data.AuthViewModel
import com.nigel.energym.navigation.ROUTE_REGISTER


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController) {
    val authViewModel: AuthViewModel = viewModel()
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Scaffold(
//            topBar = {
//                TopAppBar(title = { Text(
//                    text ="Energym",
//                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
//                    color = MaterialTheme.colorScheme.onPrimary) })
//            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(20.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            )  {
                // Logo and Title
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
//                    Image(
//                        painter = painterResource(R.drawable.logo),
//                        contentDescription = "Swift Trans Logo",
//                        modifier = Modifier
//                            .size(48.dp)
//                            .padding(end = 8.dp)
//                    )
                    Text(
                        text = "Login Here",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.Black,
                        fontSize = 36.sp
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))


                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        authViewModel.login(email, password, navController, context)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text("Login")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(onClick = {
                    navController.navigate(ROUTE_REGISTER)
                }) {
                    Text(
                        text = buildAnnotatedString { append("Don't have an account? Register Here ") },
                        modifier = Modifier.wrapContentWidth().clickable {
                            navController.navigate(ROUTE_REGISTER)
                        })
                }
            }
        }}



//    var email by remember { mutableStateOf(TextFieldValue("")) }
//    var password by remember { mutableStateOf(TextFieldValue("")) }
//    var confirmpassword by remember { mutableStateOf(TextFieldValue("")) }
//    var userName by remember { mutableStateOf(TextFieldValue("")) }
//
//
//    var context = LocalContext.current
//
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier
//            .padding(16.dp)
//            .fillMaxSize()
//    ) {
//        Column(
//            modifier = Modifier.background(Color.Cyan).padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//
//            Text(
//                text = "Login ",
//                textAlign = TextAlign.Center,
//                textDecoration = TextDecoration.Underline,
//                fontFamily = FontFamily.Cursive,
//                fontSize = 30.sp,
//                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
//            )
////            EMAIL INPUT
//            TextField(
//                value = email, onValueChange = { email = it },
//                label = { Text(text = "Enter Email") },
//                leadingIcon = { Icon(Icons.Default.Email , contentDescription = "Email")},
//                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding( vertical=8.dp),
//                shape = RoundedCornerShape(60.dp),
//            )
////            USERNAMEINPUT
//            TextField( value = userName,
//                onValueChange = {userName=it},
//                label = { Text("Username ") },
//                leadingIcon = { Icon(Icons.Default.AccountCircle , contentDescription = "User")},
//                placeholder = { Text("Enter your Username") },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding( vertical = 8.dp),
//                shape = RoundedCornerShape(60.dp)
//
//            )
//
//
//
//
//
////            PASSWORD INPUT
//            TextField(value =password , onValueChange = {password=it},
//            label = { Text(text = "Enter Password")
//                    FontFamily.Monospace},
//                leadingIcon = {Icon(Icons.Default.Lock , contentDescription = "Password")},
//            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical =  8.dp),
//            shape = RoundedCornerShape(60.dp),
//                )
//
//            TextField(
//                value = confirmpassword, onValueChange = { confirmpassword = it },
//                label = { Text(" Confirm Password")
//                    FontFamily.Monospace},
//                leadingIcon = {Icon(Icons.Default.Lock , contentDescription = "Password")},
//                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical =  8.dp),
//                shape = RoundedCornerShape(60.dp),
//
//            )
//
//
////            LOGINBUTTON
//            Button(onClick = {
//                navController.navigate(ROUTE_HOME){
//                    val mylogin= AuthViewModel(navController, context )
//                    mylogin.login(email.text.trim(),password.text.trim(),confirmpassword.text.trim())
//
//                }
//            }, modifier = Modifier
//                .width(100.dp)
//                .height(50.dp)) {
//                Text("Log in")
//            }
//
//
//        }
//    }
}

@Preview
@Composable
private fun Loginpage() {
    LoginScreen( rememberNavController())

}




