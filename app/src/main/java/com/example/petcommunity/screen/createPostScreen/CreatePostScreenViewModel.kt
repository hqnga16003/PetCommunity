package com.example.petcommunity.screen.createPostScreen

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.petcommunity.data.PostRepository
import com.example.petcommunity.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@Stable
interface CreatePostScreenState {
    var address: String
    var content: String
    var phoneNumber: String
    var quantity: String
    var capturedImageUri: Uri
}

private class MutableCreatePostScreenState : CreatePostScreenState {
    override var address: String by mutableStateOf("42/2 Lương Ngọc Quyến Phường 5 Gò Vấp Tp Hồ Chí Minh")
    override var content: String by mutableStateOf("Nhà có 3 con chóa")
    override var phoneNumber: String by mutableStateOf("0773346307")
    override var quantity: String by mutableStateOf("")
    override var capturedImageUri: Uri by mutableStateOf(Uri.EMPTY)

}

@HiltViewModel
class CreatePostScreenViewModel @Inject constructor(
    val postRepository: PostRepository
) : ViewModel() {
    private val _uiState = MutableCreatePostScreenState()
    val uiState: CreatePostScreenState get() = _uiState

    init {

        Log.d("XXX", "CreatePostScreenViewModel")
    }

    fun addPost(post: Post,uId: String,uri: Uri) {
        postRepository.addPost(post,uId,uri)
    }


    fun init(){
        _uiState.address = ""
        _uiState.content = ""
        _uiState.capturedImageUri = Uri.EMPTY
        _uiState.phoneNumber = ""
        _uiState.quantity = ""
    }

}