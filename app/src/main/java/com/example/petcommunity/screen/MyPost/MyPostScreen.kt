package com.example.petcommunity.screen.MyPost

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.petcommunity.R
import com.example.petcommunity.SharePreferences
import com.example.petcommunity.model.Pet
import com.example.petcommunity.screen.homeScreen.HomeViewModel
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MyPostScreen(navHostController: NavHostController, homeViewModel: HomeViewModel) {
    val listPet = homeViewModel.listPet.collectAsState()
    val context = LocalContext.current

    val uId = FirebaseAuth.getInstance().uid.toString()
    Log.d("XXXX",uId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Post") },
                backgroundColor = MaterialTheme.colors.background,
                contentColor = colorResource(id = R.color.text),
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp, 24.dp)
                            .clickable {

                            },
                        tint = colorResource(id = R.color.text)
                    )
                }
            )
        },

        content = { it ->
            LazyColumn(modifier = Modifier.padding(it)) {

                items(listPet.value.filter {
                    it.uid == uId && it.check == true }) { it ->
                    ItemPost(it){
                        homeViewModel.changePost(it.id!!, !it.userCheck!!)
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemPost(dog: Pet, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp)),
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.onSurface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            GlideImage(
                modifier = Modifier
                    .size(80.dp, 80.dp)
                    .clip(RoundedCornerShape(16.dp)),
                model = dog.image,
                alignment = Alignment.CenterStart,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SwitchMinimalExample(dog.userCheck!!,onClick)
            }
        }
    }
}

@Composable
fun SwitchMinimalExample(boolean: Boolean,onClick: () -> Unit) {
    var checked by remember { mutableStateOf(boolean) }

    Switch(
        checked = checked,
        onCheckedChange = {
            checked = !checked
            onClick()
        }
    )
}