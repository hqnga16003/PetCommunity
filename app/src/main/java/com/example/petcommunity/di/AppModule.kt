package com.example.petcommunity.di

import com.example.petcommunity.data.AuthRepository
import com.example.petcommunity.data.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth  = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl;
}