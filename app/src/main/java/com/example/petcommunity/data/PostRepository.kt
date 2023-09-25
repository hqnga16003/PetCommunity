package com.example.petcommunity.data

import android.net.Uri
import android.util.Log
import com.example.petcommunity.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class PostRepository @Inject constructor(
    val firebaseStorage: FirebaseStorage,
    val firebaseFireStore: FirebaseFirestore
) {
    fun addPost(post: Post,uId: String,uri: Uri) {
        firebaseFireStore.collection("post").add(post).addOnCompleteListener {
            addImg(uId,it.result.id,uri)
        }.addOnFailureListener {

        }
    }

    fun addImg(uId:String,postId:String,uri: Uri){
        val imgPet = firebaseStorage.reference.child("$uId/$postId.jpg")
        var uploadTask = imgPet.putFile(uri)
        uploadTask.addOnCanceledListener {

        }.addOnSuccessListener{

        }

    }
}