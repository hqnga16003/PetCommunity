package com.example.petcommunity.di

import com.example.petcommunity.data.AuthRepository
import com.example.petcommunity.data.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{


    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth  = FirebaseAuth.getInstance()
    @Singleton
    @Provides
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()
    @Singleton
    @Provides
    fun provideFirebaseFireStore(): FirebaseFirestore = Firebase.firestore

    @Singleton
    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl;
}