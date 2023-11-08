package com.example.petcommunity.screen.userScreen

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.petcommunity.BottomBarScaffold
import com.example.petcommunity.FloatingActionButtonScaffold
import com.example.petcommunity.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


@Composable
fun UserScreen(navHostController: NavHostController, onClick: () -> Unit) {
    val configuration = LocalConfiguration.current
    val height = configuration.screenHeightDp * 0.1
    val width = configuration.screenWidthDp
    val firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    val context = LocalContext.current
    val activity = (LocalContext.current as? Activity)

    Scaffold(floatingActionButton = {
        FloatingActionButtonScaffold(navHostController)
    },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        bottomBar = {
            BottomBarScaffold(navHostController)
        }) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ImageAvata(modifier = Modifier.size(height.dp))
                    Column {
                        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                            Text(
                                text = FirebaseAuth.getInstance().currentUser?.email.toString(),
                                style = TextStyle(fontWeight = FontWeight.Bold),
                                fontSize = 20.sp
                            )
                            Icon(Icons.Default.Create, contentDescription = "", tint = Color.Green)
                        }

                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                Item(title = "My Post", icons = Icons.Default.AccountCircle) {
                    navHostController.navigate("myPost")
                }
                Item(title = "My Booking Store", icons = Icons.Default.AccountCircle) {
                    navHostController.navigate("myOrder")
                }

                Item(title = "My Customer Order", icons = Icons.Default.AccountCircle) {
                    navHostController.navigate("myBooking")
                }

                Item(title = "Delivery Address", icons = Icons.Default.LocationOn) {
                    navHostController.navigate("registerScreen")
                }


                Item(title = "Notifications", icons = Icons.Default.Notifications) {

                }
                Item(title = "Help", icons = Icons.Default.Info) {
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
                    startActivity(context, browserIntent, null)
                }
                Item(title = "About", icons = Icons.Default.Email) {
                    val intent = Intent(Intent.ACTION_SENDTO)
                    intent.data =
                        Uri.parse("mailto:2051010201nga@gmail.com") // only email apps should handle this
                    startActivity(context, intent, null)
                }

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {
                        FirebaseAuth.getInstance().signOut()
                        navHostController.popBackStack(0,false,false);
                        navHostController.navigate("login")

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "", tint = Color.Green)
                    Text(text = "Log Out", fontSize = 18.sp, color = Color.Green)
                }

            }
        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageAvata(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(CircleShape)
            .border(1.dp, Color.Gray, CircleShape)
    )
}

@Composable
private fun Item(title: String, icons: ImageVector, onClick: () -> Unit) {
    Spacer(modifier = Modifier.height(15.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Icon(icons, contentDescription = "", modifier = Modifier.weight(0.5f))
        Text(
            text = title,
            style = TextStyle(fontWeight = FontWeight.Bold),
            fontSize = 20.sp,
            modifier = Modifier.weight(4f)
        )
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "",
            modifier = Modifier.weight(0.5f)
        )
    }
    Spacer(modifier = Modifier.height(15.dp))
    Divider(color = Color.Gray, thickness = 1.dp)
}