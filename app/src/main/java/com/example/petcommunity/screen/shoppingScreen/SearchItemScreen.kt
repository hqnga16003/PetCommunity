package com.example.petcommunity.screen.shoppingScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.petcommunity.component.ItemDogCard
import com.example.petcommunity.model.PetBookNavigation
import com.example.petcommunity.screen.homeScreen.HomeViewModel

@Composable
fun SearchItemScreen(
    navHostController: NavHostController,
    title: String,
    homeViewModel: HomeViewModel
) {
    val listPet = homeViewModel.listPet.collectAsState()
    Scaffold { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(listPet.value.filter { it.check == true && it.userCheck == true && it.location == title }) { it ->
                ItemDogCard(dog = it) {
                    navHostController.navigate(PetBookNavigation.createRoute(it))

                }
            }
        }
    }

}