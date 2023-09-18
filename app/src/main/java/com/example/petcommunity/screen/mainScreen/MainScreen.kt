package com.example.petcommunity.screen.mainScreen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.petcommunity.navigation.Navigation

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Navigation(navController = navController)
}