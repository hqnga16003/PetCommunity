package com.example.petcommunity.screen

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.petcommunity.model.PetLocation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun RegisterScreen(navController: NavHostController) {
    var city by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("") }
    var ward by remember { mutableStateOf("") }
    var street by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    val db = FirebaseFirestore.getInstance()
    val uId = FirebaseAuth.getInstance().uid.toString()

    Scaffold(topBar = {
        TopAppBar(title = { Text("Register Location") })
    }) { paddingValues ->
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "Enter Your name store") },
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text(text = "Enter Your City") },
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = district,
                    onValueChange = { district = it },
                    label = { Text(text = "Enter Your district") },
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                        value = ward,
                onValueChange = { ward = it },
                label = { Text(text = "Enter Your ward") },
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = street,
                    onValueChange = { street = it },
                    label = { Text(text = "Enter Your Street") },
                )
                Spacer(modifier = Modifier.height(10.dp))
                
                OutlinedButton(onClick = {
                    val petLocation = PetLocation("",name,city,district,ward,street,"0",uId,false)
                    db.collection("PetLocation").add(petLocation).addOnSuccessListener {
                        val washingtonRef = db.collection("PetLocation").document(it.id)
                        washingtonRef
                            .update("id", it.id)
                            .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully updated!") }
                            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error updating document", e) }
                    }.addOnFailureListener {

                    }
                    navController.navigate("ContactScreen")
                }) {
                    Text(text = "Register now")
                }
            }
        }
    }
}