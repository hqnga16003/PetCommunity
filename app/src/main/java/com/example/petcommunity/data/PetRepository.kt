package com.example.petcommunity.data

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import com.example.petcommunity.model.Pet
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PetRepository @Inject constructor(
    val firebaseStorage: FirebaseStorage,
    val firebaseFireStore: FirebaseFirestore
) {
    fun addPost(pet: Pet, uId: String, uri: Uri)  {
        firebaseFireStore.collection("pet").add(pet).addOnCompleteListener {
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
                listPet.add( document.toObject<Pet>())
            }
        return listPet

    }
}