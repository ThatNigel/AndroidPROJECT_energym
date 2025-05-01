package com.nigel.energym.Model

import kotlin.time.Duration.Companion.seconds


class Upload{
    var Workout:String=""
    var Time: Int=0
    var id:String=""

    constructor(name:String,quantity:String,price:String,imageUrl:String,id:String){

        this.Workout=name
        this.Time=seconds
        this.id=id

    }
    constructor()
}