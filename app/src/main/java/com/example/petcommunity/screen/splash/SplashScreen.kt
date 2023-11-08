package com.example.petcommunity.screen.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.petcommunity.BottomBarScreen
import com.example.petcommunity.R
import com.example.petcommunity.SharePreferences
import kotlinx.coroutines.delay
import okhttp3.internal.wait


@Composable
fun SplashScreen(navController: NavHostController) {
    var showLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        delay(3000)
        showLoading = false
        navController.popBackStack()
       if (SharePreferences.checkWelcome(context)){
           navController.navigate("login")
       }else{
           navController.navigate("welcome")

       }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(250.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            LoadingAnimation(
                dotCount = 5,
                circleSize = 10.dp,
                circleColor = Color.Gray,
                spaceBetween = 10.dp,
                travelDistance = 20.dp,

                )
        }
    }
}
@Composable
fun LoadingAnimation(
    dotCount: Int = 3,
    circleSize: Dp = 25.dp,
    circleColor: Color = Color.Gray,
    spaceBetween: Dp = 10.dp,
    travelDistance: Dp = 20.dp,
    waveAmplitude: Dp = 30.dp,
    waveFrequency: Float = 8f,
    modifier: Modifier = Modifier
) {
    val waveOffsets = remember { List(dotCount) { Animatable(0f) } }
    waveOffsets.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable) {
            delay(index * 100L)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1200
                        0.0f at 0 with LinearOutSlowInEasing
                        1.0f at 600 with LinearOutSlowInEasing
                        0.0f at 1200 with LinearOutSlowInEasing
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }
    val waveOffsetsValues = waveOffsets.map { it.value }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceBetween)
    ) {
        waveOffsetsValues.forEachIndexed { index, value ->

            Box(
                modifier = Modifier
                    .size(circleSize)
                    .graphicsLayer {
                        translationY = waveOffsetsValues[index] * waveAmplitude.toPx()
                    }
                    .background(
                        color = circleColor,
                        shape = CircleShape
                    )
            )
        }
    }
}