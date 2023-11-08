package com.example.petcommunity.screen.MyPost

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcommunity.data.PetRepository
import com.example.petcommunity.model.Pet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPostViewModel @Inject constructor(var petRepository: PetRepository) : ViewModel() {
    private val _listPet = MutableStateFlow<List<Pet>>(emptyList())
    val listPet: StateFlow<List<Pet>> get() = _listPet

    fun getMyPost(uId: String, onSuccess: (List<Pet>) -> Unit) {
        petRepository.getMyPost(uId,onSuccess)
    }

}