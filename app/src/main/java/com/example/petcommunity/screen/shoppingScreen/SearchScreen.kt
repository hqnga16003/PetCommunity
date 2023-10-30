package com.example.petcommunity.screen.shoppingScreen

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.petcommunity.BottomBarScaffold
import com.example.petcommunity.FloatingActionButtonScaffold
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun ShoppingScreen(navHostController: NavHostController) {

    val listPost = listOf(
        "https://bizweb.dktcdn.net/100/092/840/themes/885495/assets/slider_1.jpg?1686585534643",
        "https://bizweb.dktcdn.net/100/092/840/themes/885495/assets/slider_2.jpg?1686585534643",
        "https://bizweb.dktcdn.net/100/092/840/themes/885495/assets/slider_3.jpg?1686585534643"
    )
    val height = LocalConfiguration.current.screenHeightDp
    val heightImg = height * 0.2
    Scaffold(floatingActionButton = {
        FloatingActionButtonScaffold(navHostController)
    },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        bottomBar = {
            BottomBarScaffold(navHostController)
        }) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            item {
                val pagerState = rememberPagerState(pageCount = {
                    listPost.size
                })
                GlideImage(
                    model = "https://bizweb.dktcdn.net/100/092/840/themes/885495/assets/slider_3.jpg?1686585534643",
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )
//                HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth()) { page ->
//                    Poster(uri = listPost[page], modifier = Modifier.fillMaxWidth().height(heightImg.dp))
//                }

            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Poster(uri: String, modifier: Modifier = Modifier) {
    GlideImage(
        model = uri,
        contentDescription = "",
        modifier = modifier,
        contentScale = ContentScale.Fit
    )
}