package com.example.petcommunity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.petcommunity.screen.createPostScreen.CreatePostScreenViewModel
import com.example.petcommunity.screen.mainScreen.MainScreen
import com.example.petcommunity.ui.theme.PetCommunityTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: CreatePostScreenViewModel = hiltViewModel()

            PetCommunityTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(viewModel)
                }
            }
        }
    }
}

