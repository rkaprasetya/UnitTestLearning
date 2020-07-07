package com.raka.mockitotutorial.greeter

class Greeter(private val user: User) {
    fun getEnglishGreeter() = "Hello ${user.fullName()}"
}

class User(private val firstName:String,private val lastName:String){
    fun fullName():String = "$firstName $lastName"
}