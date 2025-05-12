package com.nigel.energym.data

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nigel.energym.Model.UserModel
import com.nigel.energym.navigation.ROUTE_HOME
import kotlinx.coroutines.flow.MutableStateFlow

class AuthViewModel:ViewModel() {
    private val mAuth = FirebaseAuth.getInstance()
    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage = mutableStateOf<String?>(null)

    fun signup(name:String, email:String, password:String,
               navController: NavController, context: Context){
        if (name.isBlank() || email.isBlank() || password.isBlank()){
            Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
            return
        }
        _isLoading.value = true

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {task->
                _isLoading.value = false
                if (task.isSuccessful){
                    val userId = mAuth.currentUser?.uid?:""
                    val userData = UserModel(name = name, email = email, password = password, userId = userId)
                    saveUserToDatabase(userId,userData,navController, context)

                }else{
                    _errorMessage.value = task.exception?.message
                    Toast.makeText(context,"Registration failed", Toast.LENGTH_LONG).show()
                }
            }
    }fun saveUserToDatabase(userId:String, userData: UserModel,
                            navController: NavController, context: Context){
        val regRef = FirebaseDatabase.getInstance()
            .getReference("Users/$userId")
        regRef.setValue(userData).addOnCompleteListener {regRef->
            if (regRef.isSuccessful){
                Toast.makeText(context, "User Successfully Registered", Toast.LENGTH_LONG).show()
                navController.navigate(ROUTE_HOME)
            }else{
                _errorMessage.value = regRef.exception?.message
                Toast.makeText(context, "Database error", Toast.LENGTH_LONG).show()

            }
        }
    }
    fun login(email:String, password:String, navController: NavController, context: Context){
        if (email.isBlank()|| password.isBlank()){
            Toast.makeText(context,"Email and password required",Toast.LENGTH_LONG).show()
            return
        }
        _isLoading.value = true
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {task->
                _isLoading.value = false
                if (task.isSuccessful) {
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_HOME)
                }else{
                    _errorMessage.value = task.exception?.message
                    Toast.makeText(context, "Login failed", Toast.LENGTH_LONG).show()
                }
            }

    }

}








//
//import android.app.ProgressDialog
//import android.content.Context
//import android.widget.Toast
//import androidx.navigation.NavHostController
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.FirebaseDatabase
//import com.nigel.energym.Model.User
//import com.nigel.energym.navigation.ROUTE_HOME
//import com.nigel.energym.navigation.ROUTE_LOGIN
//
//
//class AuthViewModel(var navController:NavHostController,var context:Context){
//
//    var mAuth: FirebaseAuth
//    val progress: ProgressDialog
//
//    init {
//        mAuth= FirebaseAuth.getInstance()
//        progress=ProgressDialog(context)
//        progress.setTitle("Loading")
//        progress.setMessage("PLease Wait.....")
//    }
//    fun login(email:String, password:String, confirmpassword: String){
//        progress.show()
//
//        if (email.isBlank() || password.isBlank() ||confirmpassword.isBlank()){
//            progress.dismiss()
//            Toast.makeText(context,"Please email and password cannot be blank",Toast.LENGTH_LONG).show()
//            return
//        }else if (password != confirmpassword){
//            Toast.makeText(context,"Password do not match",Toast.LENGTH_LONG).show()
//            return
//        }else{
//            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
//                if (it.isSuccessful){
//                    val userdata= User(email,password,mAuth.currentUser!!.uid)
//                    val regRef= FirebaseDatabase.getInstance().getReference()
//                        .child("Users/"+mAuth.currentUser!!.uid)
//                    regRef.setValue(userdata).addOnCompleteListener {
//
//                        if (it.isSuccessful){
//                            Toast.makeText(context,"Registered Successfully",Toast.LENGTH_LONG).show()
//                            navController.navigate(ROUTE_LOGIN)
//
//                        }else{
//                            Toast.makeText(context,"${it.exception!!.message}",Toast.LENGTH_LONG).show()
//                            navController.navigate(ROUTE_LOGIN)
//                        }
//                    }
//                }
//
//            } }
//
//    }
////    fun login(email: String,password: String){
////        progress.show()
////
////        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
////            progress.dismiss()
////            if (it.isSuccessful){
////                Toast.makeText(context,"Succeffully Logged in",Toast.LENGTH_LONG).show()
////                navController.navigate(ROUTE_HOME)
//////                navController.navigate(ROUTE_REGISTER)TO TAKE YOU TO A DIIFFERNT PAGE
////            }else{
////                Toast.makeText(context,"${it.exception!!.message}",Toast.LENGTH_LONG).show()
////                navController.navigate(ROUTE_LOGIN)
////            }
////        }
////
////    }
//    fun logout(){
//        mAuth.signOut()
//        navController.navigate(ROUTE_LOGIN)
//    }
//    fun isloggedin():Boolean{
//        return mAuth.currentUser !=null
//    }}
//
//
