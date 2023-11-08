package com.example.petcommunity.screen.settingsScreen

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingFlowParams.ProductDetailsParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.SkuDetailsParams
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.petcommunity.BottomBarScaffold
import com.example.petcommunity.FloatingActionButtonScaffold
import com.example.petcommunity.R
import com.example.petcommunity.component.GenderTag
import com.example.petcommunity.model.Pet
import com.example.petcommunity.model.PetBookNavigation
import com.example.petcommunity.model.PetLocation
import com.example.petcommunity.model.PetLocationBookNavigation
import com.example.petcommunity.screen.homeScreen.HomeViewModel
import com.google.common.collect.ImmutableList
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

@Composable
fun SettingsScreen(navHostController: NavHostController) {
    val context = LocalContext.current
    val homeViewModel : HomeViewModel = hiltViewModel()
    val listPetLocation = homeViewModel.listLocationPet.collectAsState()

    Scaffold(floatingActionButton = {
        FloatingActionButtonScaffold(navHostController)
    },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        bottomBar = {
            BottomBarScaffold(navHostController)
        }) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(listPetLocation.value.filter { it.activity== true }) {it->
                ItemPetLocation(it){
                    navHostController.navigate(PetLocationBookNavigation.createRoute(it))

                }
            }
        }
    }


}

@Composable
fun ItemPetLocation(location: PetLocation, onItemClicked: (location: PetLocation) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = { onItemClicked(location) }),
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.onSurface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Image(
                modifier = Modifier
                    .size(80.dp, 80.dp)
                    .clip(RoundedCornerShape(16.dp)),
                painter = painterResource(id = R.drawable.location_default),
                alignment = Alignment.CenterStart,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                Text(
                    text = location.name.toString(),
                    modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                    color = MaterialTheme.colors.surface,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.subtitle1
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = buildString {
                        append(location.street)
                        append(" | ")
                        append(location.ward)
                        append(" | ")
                        append(location.district)
                    },
                    modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                    color = MaterialTheme.colors.surface,
                    style = MaterialTheme.typography.caption
                )

                Row(verticalAlignment = Alignment.Bottom) {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp, 16.dp),
                        tint = Color.Red
                    )

                    Text(
                        text = location.city.toString(),
                        modifier = Modifier.padding(8.dp, 12.dp, 12.dp, 0.dp),
                        color = MaterialTheme.colors.surface,
                        style = MaterialTheme.typography.caption
                    )
                }
            }


        }
    }
}
