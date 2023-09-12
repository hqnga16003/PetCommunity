package com.example.petcommunity.screen.settingsScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.petcommunity.BottomBarScaffold
import com.example.petcommunity.FloatingActionButtonScaffold

@Composable
fun SettingsScreen(navController: NavController) {
    Scaffold(floatingActionButton = {
        FloatingActionButtonScaffold()
    },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        bottomBar = {
            BottomBarScaffold(navController)
        }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Text(text = "FavoriteScreen")
        }
    }
}