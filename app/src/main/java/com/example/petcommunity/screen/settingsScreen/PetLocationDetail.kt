package com.example.petcommunity.screen.settingsScreen

import android.annotation.SuppressLint
import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.petcommunity.R
import com.example.petcommunity.data.FakeDogDatabase
import com.example.petcommunity.model.Booking
import com.example.petcommunity.model.PetLocation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.time.LocalTime

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PetLocationDetail(navController: NavHostController, petLocation: PetLocation) {

    val db = FirebaseFirestore.getInstance()
    val uId = FirebaseAuth.getInstance().uid.toString()

    Log.d("VVVVVV",petLocation.toString())
    Scaffold(topBar = {
        TopAppBar(title = { Text("Details") },
            backgroundColor = MaterialTheme.colors.background,
            contentColor = colorResource(id = R.color.text),
            navigationIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp, 24.dp)
                        .clickable {
                            navController.navigateUp()
                        },
                    tint = colorResource(id = R.color.text)
                )
            })
    },

        content = {
            DetailsView(petLocation, db, uId)
        })
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailsView(petLocation: PetLocation, db: FirebaseFirestore, uId: String) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background))
    ) {

        item {
            petLocation.apply {

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(346.dp),
                    painter = painterResource(id = R.drawable.location_default),
                    alignment = Alignment.CenterStart,
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))
                DogInfoCard(petLocation)
            }
        }

        // My story details
        item {
            petLocation.apply {

                Spacer(modifier = Modifier.height(24.dp))
                Title(title = "About")
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Địa điểm trông giữ thú cưng, thường gọi là trung tâm trông giữ thú cưng hoặc bệnh viện thú y, là nơi cung cấp chăm sóc và quan tâm đến sức khỏe, an toàn, và hạnh phúc của các thành viên thú cưng trong gia đình. Tại những địa điểm này, chuyên gia thú y và nhân viên quan tâm đến việc cung cấp dịch vụ từ việc điều trị y tế, kiểm tra sức khỏe định kỳ cho đến chăm sóc và nuôi dưỡng động vật cưng. Địa điểm trông giữ thú cưng thường đảm bảo rằng bạn có một môi trường an toàn và tin cậy để đảm bảo thú cưng của bạn có một cuộc sống khỏe mạnh và hạnh phúc.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    color = colorResource(id = R.color.text),
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Start
                )
            }
        }

        item {
            petLocation.apply {
                Spacer(modifier = Modifier.height(24.dp))
                Title(title = "Shop Name")
                Spacer(modifier = Modifier.height(16.dp))
                FakeDogDatabase.owner.apply {
                    OwnerCard(petLocation.name.toString())
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(36.dp))
            Button(
                onClick = {
                    val address =
                        petLocation.street + " " + petLocation.ward + " " + petLocation.district + " " + petLocation.city
                    val booking = Booking(
                        id = "",
                        timeBooking = LocalDate.now().toString(),
                        confirm = false,
                        addressPet = "42/2 Lương Ngọc Quyến Phường 5 Gò Vấp",
                        uidBooking = uId,
                        idPetLocation = petLocation.uid,
                        shopName = petLocation.name,
                        addressShop = address
                    )
                    val booking1 = Booking()
                    db.collection("Booking").add(booking).addOnSuccessListener {
                        val washingtonRef = db.collection("Booking").document(it.id)
                        washingtonRef.update("id", it.id).addOnSuccessListener {
                            Log.d(
                                ContentValues.TAG, "DocumentSnapshot successfully updated!"
                            )
                        }.addOnFailureListener { e ->
                            Log.w(
                                ContentValues.TAG, "Error updating document", e
                            )
                        }
                    }.addOnFailureListener {

                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = colorResource(id = R.color.blue), contentColor = Color.White
                )
            ) {
                Text("Book me")
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun Title(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 0.dp, 0.dp),
        color = colorResource(id = R.color.text),
        style = MaterialTheme.typography.subtitle1,
        fontWeight = FontWeight.W600,
        textAlign = TextAlign.Start
    )
}


@Composable
fun DogInfoCard(petLocation: PetLocation) {
    var checked by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Text(
                text = petLocation.name.toString(),
                modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                color = MaterialTheme.colors.surface,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.Bottom) {

                val locationIcon: Painter = painterResource(id = R.drawable.ic_location)

                Icon(
                    painter = locationIcon,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp, 16.dp),
                    tint = Color.Red
                )

                Text(
                    text = petLocation.street.toString() + " " + petLocation.ward.toString() + " " + petLocation.district.toString() + " " + petLocation.city.toString(),
                    modifier = Modifier.padding(8.dp, 12.dp, 12.dp, 0.dp),
                    color = MaterialTheme.colors.surface,
                    style = MaterialTheme.typography.caption
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

    }
}


@Composable
fun OwnerCard(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            modifier = Modifier
                .size(60.dp, 60.dp)
                .clip(RoundedCornerShape(16.dp)),
            painter = painterResource(id = R.drawable.avtar_user),
            alignment = Alignment.CenterStart,
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier) {
            Text(
                text = name,
                color = colorResource(id = R.color.text),
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.W600,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(8.dp))

        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            FloatingActionButton(
                modifier = Modifier.size(40.dp),
                onClick = { /*TODO*/ },
                backgroundColor = colorResource(id = R.color.blue)
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_messanger),
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
    }
}

