package com.example.petcommunity.data

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currenUser: FirebaseUser?
    suspend fun login(email:String,password:String):FirebaseUser?
    suspend fun signUp(email:String,password:String):FirebaseUser?
     fun sendPasswordResetEmail(email: String)
    fun logOut()

}