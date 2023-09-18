package com.example.petcommunity.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    AuthRepository {
    override val currenUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): FirebaseUser? {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Log.d("XXX1",currenUser?.email.toString())
            firebaseAuth.currentUser
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("XXX2","12312312")

            null

        }
    }


    override suspend fun signUp(email: String, password: String): FirebaseUser? {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email,password)
            firebaseAuth.currentUser
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun logOut() {
        firebaseAuth.signOut()
    }
}