package com.example.petcommunity.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.petcommunity.BottomBarScreen
import com.example.petcommunity.R

@Composable
fun ContactScreen(navController: NavHostController) {
    val width = LocalConfiguration.current.screenWidthDp
    val height = LocalConfiguration.current.screenHeightDp
    val heightImg = height * 0.5
    val context = LocalContext.current

    Scaffold(topBar = {
        TopAppBar(title = { Text("Register Location") })
    }) { paddingValues ->
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))

                Image(
                    painter = painterResource(id = R.drawable.register_sussces),
                    contentDescription = "",
                    modifier = Modifier.size(size = heightImg.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    OutlinedButton(modifier = Modifier.weight(1f).padding(16.dp),onClick = {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:0773346307")
                        }
                        ContextCompat.startActivity(context, intent, null)
                        navController.navigate(BottomBarScreen.Home.route)
                    }) {
                        Text(text = "Contact Phone ")
                    }

                    OutlinedButton(modifier = Modifier.weight(1f).padding(16.dp),onClick = {

                    }) {
                        Text(text = "Contact Gmail")
                    }
                }
            }
        }
    }
}
