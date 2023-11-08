package com.example.petcommunity.model

import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import com.google.gson.Gson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class Pet(
    var id:String? = null,
    val name: String? = null,
    val age: String? = null,
    val gender: String? = null,
    val color: String? = null,
    val weight: String? = null,
    val location: String? = null,
    var image: String? = null,
    val content: String? = null,
    val phoneNumber: String? = null,
    val uid: String? = null,//
    val createAt: String? = null,
    val check: Boolean? = null,
    val userCheck:Boolean? = null,
    val favorite: String? = null

) : Parcelable {
    companion object {
        val gson = Gson();
        fun from(value: String) = gson.fromJson(value, Pet::class.java) ?: Pet()
    }

    override fun toString(): String {
        return gson.toJson(this)
    }

}

class PetsNavType : NavType<Pet>(isNullableAllowed = true) {
    override fun get(bundle: Bundle, key: String): Pet? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): Pet {
        return Pet.from(value)
    }

    override fun put(bundle: Bundle, key: String, value: Pet) {
        bundle.putParcelable(key, value)
    }
}

object PetBookNavigation {
    const val petArg = "petArg"
    const val route = "petBook?${petArg}={$petArg}"


    fun createRoute(pet: Pet): String {


        return "petBook?${petArg}=${pet.toString()}"
    }

    fun from(entry: NavBackStackEntry) = entry.arguments?.getParcelable<Pet>(petArg)

}