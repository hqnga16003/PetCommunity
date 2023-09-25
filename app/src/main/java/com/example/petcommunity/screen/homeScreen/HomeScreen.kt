package com.example.petcommunity.screen.homeScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.petcommunity.BottomBarScaffold
import com.example.petcommunity.FloatingActionButtonScaffold
import com.example.petcommunity.R
import com.example.petcommunity.component.ItemDogCard
import com.example.petcommunity.component.TopBar
import com.example.petcommunity.data.FakeDogDatabase
import com.example.petcommunity.model.Pet

@Composable
fun HomeScreen(navHostController: NavHostController) {
    Scaffold(floatingActionButton = {
        FloatingActionButtonScaffold(navHostController)
    },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        bottomBar = {
            BottomBarScaffold(navHostController)
        }) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues).background(Color(0xFFF5F5E8))) {
            item {
                TopBar()
                Spacer(modifier = Modifier.height(8.dp))

            }
            items(FakeDogDatabase.dogList) { it ->
                ItemDogCard(pet = it) {

                }
            }


        }
    }


}