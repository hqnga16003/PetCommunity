package com.example.petcommunity.screen.homeScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.petcommunity.BottomBarScaffold
import com.example.petcommunity.FloatingActionButtonScaffold
import com.example.petcommunity.component.ItemDogCard
import com.example.petcommunity.component.TopBar
import com.example.petcommunity.model.Pet
import com.example.petcommunity.model.PetBookNavigation
import com.google.gson.Gson

@Composable
fun HomeScreen(navHostController: NavHostController,homeViewModel: HomeViewModel) {
    val listPet = homeViewModel.listPet.collectAsState()
    Scaffold(floatingActionButton = {
        FloatingActionButtonScaffold(navHostController)
    },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        bottomBar = {
            BottomBarScaffold(navHostController)
        }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                TopBar()
                Spacer(modifier = Modifier.height(8.dp))

            }
            items(listPet.value.filter { it.check == true && it.userCheck == true }) { it ->
                ItemDogCard(dog = it) {
                    navHostController.navigate(PetBookNavigation.createRoute(it))

                }
            }


        }
    }
}

