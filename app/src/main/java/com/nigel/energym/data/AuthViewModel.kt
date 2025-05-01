package com.nigel.energym.data

import android.app.ProgressDialog
import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nigel.energym.Model.User
import com.nigel.energym.navigation.ROUTE_HOME
import com.nigel.energym.navigation.ROUTE_LOGIN


class AuthViewModel(var navController:NavHostController,var context:Context){

    var mAuth: FirebaseAuth
    val progress: ProgressDialog

    init {
        mAuth= FirebaseAuth.getInstance()
        progress=ProgressDialog(context)
        progress.setTitle("Loading")
        progress.setMessage("PLease Wait.....")
    }
    fun login(email:String, password:String, confirmpassword: String){
        progress.show()

        if (email.isBlank() || password.isBlank() ||confirmpassword.isBlank()){
            progress.dismiss()
            Toast.makeText(context,"Please email and password cannot be blank",Toast.LENGTH_LONG).show()
            return
        }else if (password != confirmpassword){
            Toast.makeText(context,"Password do not match",Toast.LENGTH_LONG).show()
            return
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                if (it.isSuccessful){
                    val userdata= User(email,password,mAuth.currentUser!!.uid)
                    val regRef= FirebaseDatabase.getInstance().getReference()
                        .child("Users/"+mAuth.currentUser!!.uid)
                    regRef.setValue(userdata).addOnCompleteListener {

                        if (it.isSuccessful){
                            Toast.makeText(context,"Registered Successfully",Toast.LENGTH_LONG).show()
                            navController.navigate(ROUTE_LOGIN)

                        }else{
                            Toast.makeText(context,"${it.exception!!.message}",Toast.LENGTH_LONG).show()
                            navController.navigate(ROUTE_LOGIN)
                        }
                    }
                }

            } }

    }
//    fun login(email: String,password: String){
//        progress.show()
//
//        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
//            progress.dismiss()
//            if (it.isSuccessful){
//                Toast.makeText(context,"Succeffully Logged in",Toast.LENGTH_LONG).show()
//                navController.navigate(ROUTE_HOME)
////                navController.navigate(ROUTE_REGISTER)TO TAKE YOU TO A DIIFFERNT PAGE
//            }else{
//                Toast.makeText(context,"${it.exception!!.message}",Toast.LENGTH_LONG).show()
//                navController.navigate(ROUTE_LOGIN)
//            }
//        }
//
//    }
    fun logout(){
        mAuth.signOut()
        navController.navigate(ROUTE_LOGIN)
    }
    fun isloggedin():Boolean{
        return mAuth.currentUser !=null
    }}


