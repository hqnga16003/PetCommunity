package com.example.petcommunity.screen.homeScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcommunity.data.PetRepository
import com.example.petcommunity.model.Booking
import com.example.petcommunity.model.Pet
import com.example.petcommunity.model.PetLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(var petRepository: PetRepository) :ViewModel() {

    private val _listPet = MutableStateFlow<List<Pet>>(emptyList())
    val listPet: StateFlow<List<Pet>> get() = _listPet

    private val _listLocationPet = MutableStateFlow<List<PetLocation>>(emptyList())
    val listLocationPet: StateFlow<List<PetLocation>> get() = _listLocationPet

    private val _listMyOrder = MutableStateFlow<List<Booking>>(emptyList())
    val listMyOrder: StateFlow<List<Booking>> get() = _listMyOrder
    init {
        getAllPet()
        getAllLocationPet()
        getAllBookingPet()
    }
    private fun getAllPet() {
        viewModelScope.launch {
            _listPet.value = petRepository.getAllPets()

        }
    }

     fun changePost(id:String,boolean: Boolean){
        petRepository.changePost(id,boolean)
    }
    fun changeConfirnBooking(id:String){
        petRepository.changeBooking(id)
    }

    private fun getAllLocationPet() {
        viewModelScope.launch {
            _listLocationPet.value = petRepository.getAllLocationPet()

        }
    }

    private fun getAllBookingPet() {
        viewModelScope.launch {
            _listMyOrder.value = petRepository.getAllOrder()

        }
    }
}