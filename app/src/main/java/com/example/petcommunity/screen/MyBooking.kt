package com.example.petcommunity.screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.petcommunity.BottomBarScreen
import com.example.petcommunity.R
import com.example.petcommunity.model.Booking
import com.example.petcommunity.model.Pet
import com.example.petcommunity.model.PetLocation
import com.example.petcommunity.screen.homeScreen.HomeViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MyBookingScreen(navHostController: NavHostController) {
    val homeViewModel : HomeViewModel = hiltViewModel()

    val listBooking = homeViewModel.listMyOrder.collectAsState()
    val listPetLocation = homeViewModel.listLocationPet.collectAsState()
    val context = LocalContext.current
    val uId = FirebaseAuth.getInstance().uid.toString()
    var petLocation = listPetLocation.value.filter { it.uid == uId }



//    for (item in listPetLocation.value.){
//        Log.d("VVVVVV1",item.uId.toString())
//
//        if (item.uId == uId){
//            petLocation = item
//            Log.d("VVVVVV",petLocation.toString())
//        }
//    }

   // Log.d("VVVVVVVVV", petLocation.uId.toString())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Customer Order") },
                backgroundColor = MaterialTheme.colors.background,
                contentColor = colorResource(id = R.color.text),
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp, 24.dp)
                            .clickable {
                                navHostController.navigate(BottomBarScreen.Home.route)
                            },
                        tint = colorResource(id = R.color.text)
                    )
                }
            )
        },

        content = { it ->
            LazyColumn(modifier = Modifier.padding(it)) {

                items(listBooking.value.filter {
                    it.idPetLocation == uId && it.confirm == false
                }) { it ->
                    ItemPost(it,homeViewModel,navHostController)

                }
            }
        }
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemPost(booking: Booking,homeViewModel: HomeViewModel,navHostController: NavHostController) {
    val color = if (booking.confirm == true) Color.Blue else Color.Red
    Card(
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.onSurface,
        border = BorderStroke(1.dp, color),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp)),
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
                painter = painterResource(id = R.drawable.order),
                alignment = Alignment.CenterStart,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(10.dp)) {
                Text(
                    text = booking.addressPet.toString(),
                    modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                    color = MaterialTheme.colors.surface,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.subtitle1
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = (booking.timeBooking.toString()),
                    modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                    color = MaterialTheme.colors.surface,
                    style = MaterialTheme.typography.caption
                )
                Spacer(modifier = Modifier.height(8.dp))

               OutlinedButton(onClick = {
                    homeViewModel.changeConfirnBooking(booking.id.toString())
                    navHostController.navigate("myBooking")
               }) {
                   Text(text = "Confirm")
               }

            }

        }
    }
}
