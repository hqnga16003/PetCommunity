package com.example.petcommunity.screen.createPostScreen

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.petcommunity.data.PetRepository
import com.example.petcommunity.model.Pet
import com.example.petcommunity.screen.authScreen.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@Stable
interface CreatePostScreenState {
    var petName: String//
    var petGender: String//
    var petAge: String//
    var address: String//
    var content: String //
    var phoneNumber: String//
    var capturedImageUri: Uri
    var petColor: String//
    var petWeight: String//


}

private class MutableCreatePostScreenState : CreatePostScreenState {
    override var petName: String by mutableStateOf("Peter")
    override var petColor: String by mutableStateOf("Red")
    override var petWeight: String by mutableStateOf("35")
    override var petGender: String by mutableStateOf("Male")
    override var petAge: String by mutableStateOf("3.5")
    override var address: String by mutableStateOf("42/2 Lương Ngọc Quyến Phường 5 Gò Vấp Tp Hồ Chí Minh")
    override var content: String by mutableStateOf("Nhà có 3 con chóa")
    override var phoneNumber: String by mutableStateOf("0773346307")
    override var capturedImageUri: Uri by mutableStateOf(Uri.EMPTY)

}

@HiltViewModel
class CreatePostScreenViewModel @Inject constructor(
    private val postRepository: PetRepository
) : ViewModel() {
    private val _uiState = MutableCreatePostScreenState()
    val uiState: CreatePostScreenState get() = _uiState



    fun addPost(pet: Pet,uId: String,uri: Uri) {
        postRepository.addPost(pet,uId,uri)
        init()
    }


    fun init(){
        _uiState.address = ""//
        _uiState.content = ""//
        _uiState.capturedImageUri = Uri.EMPTY//
        _uiState.phoneNumber = ""//
        _uiState.petWeight = ""//
        _uiState.petColor = ""//
        _uiState.petName = ""//
        _uiState.petGender = ""//
        _uiState.petAge = ""


    }

}