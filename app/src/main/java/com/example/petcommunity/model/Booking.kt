package com.example.petcommunity.model

data class Booking(
    val id:String ?= null,
    val timeBooking: String? = null,
    val confirm: Boolean? = null,
    val addressPet: String ? = null,
    val uidBooking: String ? = null,
    val idPetLocation: String ? = null,
    val shopName: String ? = null,
    val addressShop:String ? = null

)
