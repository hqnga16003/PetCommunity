package com.example.petcommunity.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.petcommunity.BottomBarScreen
import com.example.petcommunity.model.Pet
import com.example.petcommunity.model.PetBookNavigation
import com.example.petcommunity.model.PetsNavType
import com.example.petcommunity.screen.authScreen.login.LoginScreenViewModel
import com.example.petcommunity.screen.authScreen.login.LoginScreen
import com.example.petcommunity.screen.authScreen.login.LoginState
import com.example.petcommunity.screen.authScreen.forgotPassword.ForgotPasswordScreen
import com.example.petcommunity.screen.authScreen.signUp.SignUpScreen
import com.example.petcommunity.screen.createPostScreen.CreatePostScreen
import com.example.petcommunity.screen.createPostScreen.CreatePostScreenViewModel
import com.example.petcommunity.screen.detailsScreen.Details
import com.example.petcommunity.screen.favoriteScreen.FavoriteScreen
import com.example.petcommunity.screen.homeScreen.HomeScreen
import com.example.petcommunity.screen.settingsScreen.SettingsScreen
import com.example.petcommunity.screen.userScreen.UserScreen
import com.google.gson.Gson

@Composable
fun Navigation(navController: NavHostController, viewModel: CreatePostScreenViewModel) {
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
        composable("forgotPassword") {
            ForgotPasswordScreen(navController)
        }
        composable(BottomBarScreen.Home.route) {
            HomeScreen(navController)
        }

        composable(
            PetBookNavigation.route,
            arguments = listOf(navArgument(PetBookNavigation.petArg) {
                nullable = true
                type = PetsNavType()
            })
        ) {
            val pet = PetBookNavigation.from(it)



            Details(navController, pet!!)

        }
        composable(BottomBarScreen.Favorite.route) {
            FavoriteScreen(navController)
        }
        composable(BottomBarScreen.Profile.route) {
            UserScreen(navController) {
                authViewModel.logOut()
                navController.navigate("login") {
                    popUpTo(navController.graph.id)
                }
            }
        }
        composable(BottomBarScreen.Settings.route) {
            SettingsScreen(navController)
        }

        composable("createPostScreen") {
            CreatePostScreen(viewModel, navController)

        }




    }
}


