package com.example.petcommunity.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.petcommunity.BottomBarScreen
import com.example.petcommunity.screen.authScreen.LoginScreenViewModel
import com.example.petcommunity.screen.authScreen.login.LoginScreen
import com.example.petcommunity.screen.authScreen.LoginState
import com.example.petcommunity.screen.authScreen.signUp.SignUpScreen
import com.example.petcommunity.screen.favoriteScreen.FavoriteScreen
import com.example.petcommunity.screen.homeScreen.HomeScreen
import com.example.petcommunity.screen.settingsScreen.SettingsScreen
import com.example.petcommunity.screen.userScreen.UserScreen

@Composable
fun Navigation(navController: NavHostController) {
    val authViewModel: LoginScreenViewModel = hiltViewModel()
    val context = LocalContext.current


    LaunchedEffect(key1 = true) {
        if (authViewModel.loginState.value == LoginState.Success) {
            navController.navigate(BottomBarScreen.Home.route)
        }
    }

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(viewModel = authViewModel, navController = navController)
        }
        composable("signUp") {
            SignUpScreen(navController)
        }
        composable(BottomBarScreen.Home.route) {
            HomeScreen(navController = navController) {
                authViewModel.logOut()
                navController.navigate("login") {
                    popUpTo(navController.graph.id)

                }
            }
        }
        composable(BottomBarScreen.Favorite.route) {
            FavoriteScreen(navController = navController)
        }
        composable(BottomBarScreen.Profile.route) {
            UserScreen(navController = navController)
        }
        composable(BottomBarScreen.Settings.route) {
            SettingsScreen(navController = navController)
        }


    }
}