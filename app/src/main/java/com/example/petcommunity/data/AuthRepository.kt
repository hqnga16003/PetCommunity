package com.example.petcommunity.data

import com.example.petcommunity.screen.authScreen.LoginState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currenUser: FirebaseUser?
    suspend fun login(email:String,password:String)
    suspend fun signUp(email:String,password:String)
    fun logOut()

}