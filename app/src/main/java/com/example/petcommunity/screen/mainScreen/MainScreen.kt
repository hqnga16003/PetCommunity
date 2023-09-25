package com.example.petcommunity.screen.mainScreen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.petcommunity.navigation.Navigation
import com.example.petcommunity.screen.createPostScreen.CreatePostScreenViewModel

@Composable
fun MainScreen(viewModel: CreatePostScreenViewModel) {
    val navController = rememberNavController()
    Navigation(navController = navController,viewModel)
}