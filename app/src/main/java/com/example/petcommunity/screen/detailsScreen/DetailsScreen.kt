package com.example.petcommunity.screen.detailsScreen


import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.material.icons.filled.Favorite
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
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.petcommunity.R
import com.example.petcommunity.component.GenderTag
import com.example.petcommunity.data.FakeDogDatabase.owner
import com.example.petcommunity.model.Pet
import com.example.petcommunity.screen.homeScreen.HomeViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Details(navController: NavController, dog: Pet, homeViewModel: HomeViewModel) {

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
            DetailsView(dog)
        })
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailsView(dog: Pet) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background))
    ) {

        item {
            dog.apply {

                GlideImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(346.dp),
                    model = dog.image!!.replace(
                        "7KboMvBuYFfsa7zVp1jDl8LQ8y22/", "7KboMvBuYFfsa7zVp1jDl8LQ8y22%2F"
                    ),
                    alignment = Alignment.CenterStart,
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))
                DogInfoCard(dog)
            }
        }

        // My story details
        item {
            dog.apply {

                Spacer(modifier = Modifier.height(24.dp))
                Title(title = "My Story")
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = dog.content.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    color = colorResource(id = R.color.text),
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Start
                )
            }
        }

        // Quick info
        item {
            dog.apply {

                Spacer(modifier = Modifier.height(24.dp))
                Title(title = "Dog info")
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    InfoCard(title = "Age", value = dog.age.toString().plus(" yrs"))
                    InfoCard(title = "Color", value = dog.color.toString())
                    InfoCard(title = "Weight", value = weight.toString().plus("Kg"))
                }
            }
        }

        // Owner info
        item {
            dog.apply {

                Spacer(modifier = Modifier.height(24.dp))
                Title(title = "Owner info")
                Spacer(modifier = Modifier.height(16.dp))
                owner.apply {
                    OwnerCard(name)
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(36.dp))
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:${dog.phoneNumber}")
                    }
                    startActivity(context, intent, null)

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = colorResource(id = R.color.blue), contentColor = Color.White
                )
            ) {
                Text("Adopt me")
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
fun DogInfoCard(dog: Pet) {
    var checked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Text(
                text = dog.name.toString(),
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
                    text = dog.location.toString(),
                    modifier = Modifier.padding(8.dp, 12.dp, 12.dp, 0.dp),
                    color = MaterialTheme.colors.surface,
                    style = MaterialTheme.typography.caption
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "12 mins ago",
                modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                color = MaterialTheme.colors.surface,
                style = MaterialTheme.typography.overline
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                GenderTag(dog.gender.toString())
            }
            Spacer(modifier = Modifier.height(5.dp))

            Icon(Icons.Default.Favorite,
                tint = if (checked) Color.Red else Color.Black,
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        checked = !checked
                    })
        }
    }
}


@Composable
fun InfoCard(title: String, value: String) {
    Box(
        modifier = Modifier
            .size(90.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color = colorResource(id = R.color.card))
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center, modifier = Modifier.wrapContentWidth()
        ) {
            Text(
                text = value,
                modifier = Modifier.fillMaxWidth(),
                color = colorResource(id = R.color.text),
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.W600,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                color = Color.Gray,
                style = MaterialTheme.typography.overline,
                textAlign = TextAlign.Center
            )
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

