package com.nigel.energym.Model

//class User{
//    var email:String=""
//    var password:String=""
//    var userName:String=""
//    var userid:String=""
//
//    constructor(email:String,password:String,userid:String){
//        this.email=email
//        this.password=password
//        this.userName=userName
//        this.userid=userid
//
//    }
//    constructor()}
data class UserModel(
    val imageUrl: String? = null,
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var userId: String = ""
)