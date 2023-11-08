package com.example.petcommunity.navigation

import SearchScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.petcommunity.BottomBarScreen
import com.example.petcommunity.model.PetBookNavigation
import com.example.petcommunity.model.PetLocationBookNavigation
import com.example.petcommunity.model.PetLocationNavType
import com.example.petcommunity.model.PetsNavType
import com.example.petcommunity.screen.ContactScreen
import com.example.petcommunity.screen.MyBookingScreen
import com.example.petcommunity.screen.MyPost.MyPostScreen
import com.example.petcommunity.screen.RegisterScreen
import com.example.petcommunity.screen.authScreen.login.LoginScreenViewModel
import com.example.petcommunity.screen.authScreen.login.LoginScreen
import com.example.petcommunity.screen.authScreen.forgotPassword.ForgotPasswordScreen
import com.example.petcommunity.screen.authScreen.login.LoginState
import com.example.petcommunity.screen.authScreen.signUp.SignUpScreen
import com.example.petcommunity.screen.createPostScreen.CreatePostScreen
import com.example.petcommunity.screen.createPostScreen.CreatePostScreenViewModel
import com.example.petcommunity.screen.detailsScreen.Details
import com.example.petcommunity.screen.homeScreen.HomeScreen
import com.example.petcommunity.screen.homeScreen.HomeViewModel
import com.example.petcommunity.screen.settingsScreen.PetLocationDetail
import com.example.petcommunity.screen.settingsScreen.SettingsScreen
import com.example.petcommunity.screen.shoppingScreen.SearchItemScreen
import com.example.petcommunity.screen.splash.SplashScreen
import com.example.petcommunity.screen.userScreen.MyOderScreen
import com.example.petcommunity.screen.userScreen.UserScreen
import com.example.petcommunity.screen.welcomeScreen.WelcomeScreen

@Composable
fun Navigation(
    navController: NavHostController,
    viewModel: CreatePostScreenViewModel,
) {
    val authViewModel: LoginScreenViewModel = hiltViewModel()
    val context = LocalContext.current
    val homeViewModel: HomeViewModel = hiltViewModel()


    LaunchedEffect(key1 = true) {
        if (authViewModel.loginState.value == LoginState.Success) {
            navController.navigate(BottomBarScreen.Home.route)
        }
    }


    NavHost(navController = navController, startDestination = "splash") {
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
            HomeScreen(navController, homeViewModel)
        }

        composable("search/{title}") {

                navBackStackEntry ->
            val title = navBackStackEntry.arguments?.getString("title");
            SearchItemScreen(navHostController = navController, title = title!!, homeViewModel)
        }

        composable(
            PetBookNavigation.route,
            arguments = listOf(navArgument(PetBookNavigation.petArg) {
                nullable = true
                type = PetsNavType()
            })
        ) {
            val pet = PetBookNavigation.from(it)
            Details(navController, pet!!, homeViewModel)
        }
        composable(
            PetLocationBookNavigation.route,
            arguments = listOf(navArgument(PetLocationBookNavigation.petLocationArg) {
                nullable = true
                type = PetLocationNavType()
            })
        ) {
            val petLocation = PetLocationBookNavigation.from(it)
            PetLocationDetail(navController, petLocation!!)
        }
        composable(BottomBarScreen.Shopping.route) {
            SearchScreen(navController)
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

        composable("splash") {
            SplashScreen(navController = navController);
        }

        composable("welcome") {
            WelcomeScreen(navController = navController);
        }

        composable("myPost") {
            MyPostScreen(navController, homeViewModel);
        }

        composable("registerScreen") {
            RegisterScreen(navController)
        }
        composable("ContactScreen") {
            ContactScreen(navController)
        }
        composable("myOrder") {
            MyOderScreen()
        }

        composable("myBooking") {
            MyBookingScreen(navController)
        }


    }
}


