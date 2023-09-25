package com.example.petcommunity.model

data class Post(
    var address: String = "",
    var content: String = "",
    var createAt: String = "",
    var phoneNumber:Int = 0,
    var quantity: Int = 0,
    var status: Boolean = true,
    var uIdCreate: String = ""
)