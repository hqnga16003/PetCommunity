package com.example.petcommunity.data

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
            firebaseAuth.currentUser
        } catch (e: Exception) {
            e.printStackTrace()
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