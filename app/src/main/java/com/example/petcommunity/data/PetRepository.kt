package com.example.petcommunity.data

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import com.example.petcommunity.model.Booking
import com.example.petcommunity.model.Pet
import com.example.petcommunity.model.PetLocation
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PetRepository @Inject constructor(
    val firebaseStorage: FirebaseStorage,
    val firebaseFireStore: FirebaseFirestore
) {
    fun addPost(pet: Pet, uId: String, uri: Uri) {
        firebaseFireStore.collection("pet").add(pet).addOnCompleteListener {
            val washingtonRef = firebaseFireStore.collection("pet").document(it.result.id)
            washingtonRef
                .update("id", it.result.id)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
            addImg(uId, it.result.id, uri)

        }.addOnFailureListener {

        }
    }

    private fun addImg(uId: String, postId: String, uri: Uri) {
        val imgPet = firebaseStorage.reference.child("$uId/$postId.jpg")
        var uploadTask = imgPet.putFile(uri)
        uploadTask.addOnSuccessListener {
            getUriImage(postId, uId, postId);
        }

    }

    private fun updatePet(id: String, uriImage: String) {
        val washingtonRef = firebaseFireStore.collection("pet").document(id)
        washingtonRef
            .update("image", uriImage)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating document", e)
            }
    }

    private fun getUriImage(id: String, uId: String, postId: String) {
        firebaseStorage.reference.child("$uId/$postId.jpg").downloadUrl.addOnSuccessListener {
            updatePet(id, it.toString())
        }
    }


    suspend fun getAllPets(): List<Pet> {
        var listPet: MutableList<Pet> = mutableListOf<Pet>()
        val result = firebaseFireStore.collection("pet").get().await()
        for (document in result) {
            val pet = document.toObject<Pet>()
            pet.id = document.id
            listPet.add(pet)

        }
        return listPet

    }
    suspend fun getAllLocationPet(): List<PetLocation> {
        val list: MutableList<PetLocation> = mutableListOf<PetLocation>()
        val result = firebaseFireStore.collection("PetLocation").get().await()
        for (document in result) {
            val petLocation = document.toObject<PetLocation>()
            list.add(petLocation)

        }
        return list

    }

    suspend fun getAllOrder(): List<Booking> {
        val list: MutableList<Booking> = mutableListOf<Booking>()
        val result = firebaseFireStore.collection("Booking").get().await()
        for (document in result) {
            val booking = document.toObject<Booking>()
            list.add(booking)
        }
        return list

    }
     fun getMyPost(uId: String, onSuccess: (List<Pet>) -> Unit) {

        var listPet: MutableList<Pet> = mutableListOf<Pet>()
        val result = firebaseFireStore.collection("pet")
            .whereEqualTo("uid", uId).get().addOnSuccessListener { documents ->
                for (document in documents) {
                    listPet.add(document.toObject<Pet>())
                    Log.d("XXXXX",listPet.size.toString())
                }
                onSuccess(listPet)
            }
    }

    fun changePost(id:String,boolean: Boolean){
        val washingtonRef = firebaseFireStore.collection("pet").document(id)
        washingtonRef
            .update("userCheck", boolean)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
    }

    fun changeBooking(id:String){
        val washingtonRef = firebaseFireStore.collection("Booking").document(id)
        washingtonRef
            .update("confirm", true)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
    }

   
}
