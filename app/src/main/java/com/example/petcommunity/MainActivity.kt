package com.example.petcommunity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.example.petcommunity.presentation.GoogleAuthUiClient
import com.example.petcommunity.screen.createPostScreen.CreatePostScreenViewModel
import com.example.petcommunity.screen.mainScreen.MainScreen
import com.example.petcommunity.ui.theme.MyTheme
import com.google.android.gms.auth.api.identity.Identity
import com.google.common.collect.ImmutableList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            // To be implemented in a later section.
        }

    lateinit var billingClient: BillingClient
    override fun onCreate(savedInstanceState: Bundle?) {

        billingClient  = BillingClient.newBuilder(this)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()


        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: CreatePostScreenViewModel = hiltViewModel()

            val currentTheme = isSystemInDarkTheme()
            val toggleTheme: () -> Unit = {
                if (currentTheme) setDayTheme() else setDarkTheme()
            }
            MyTheme {
                androidx.compose.material.Surface(color = androidx.compose.material.MaterialTheme.colors.background) {
                    MainScreen(viewModel)

                }
            }
        }

    }


    private fun setDayTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun setDarkTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}

//@ExperimentalAnimationApi
//@Preview("Dark Theme", widthDp = 360, heightDp = 640)
//@Composable
//fun DarkPreview() {
//    MyTheme(darkTheme = true) {
//
//    }
//}



@Preview(showSystemUi = true)
@Composable
fun DarkPreview() {
   Column() {
       TextDemo("hello1")
       TextDemo("hello2")
       Row {
           Text(text = "hello world")
           Text(text = "hello world")
       }
       Button(onClick = {

       }) {
            Icon(Icons.Default.ShoppingCart , contentDescription ="" )
           Text(text = "hello world")

       }

       LazyColumn(){
           items(10){
               Text(text = "hello world")
           }
       }
   }
}


@Composable
fun TextDemo(title:String) {
    Text(text = title, fontSize =20.sp, color = Color.Green)
}





