package com.example.petcommunity.screen.homeScreen

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
class HomeViewModel @Inject constructor(var petRepository: PetRepository) :ViewModel() {

    private val _listPet = MutableStateFlow<List<Pet>>(emptyList())
    val listPet: StateFlow<List<Pet>> get() = _listPet

    init {
        getAllMovies()
    }
    private fun getAllMovies() {
        viewModelScope.launch {
            _listPet.value = petRepository.getAllPets()
            Log.d("XXX2",listPet.value.size.toString())

        }
    }

}