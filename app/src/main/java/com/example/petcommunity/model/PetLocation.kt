package com.example.petcommunity.model

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.parcelize.Parcelize

@Parcelize
data class PetLocation(
    val id:String?= null,
    val name: String?= null,
    val city: String?= null,
    val district: String?= null,
    val ward: String?= null,
    val street: String?= null,
    val reviews: String?= null,
    val uid: String?= null,
    val activity: Boolean?= null
) : Parcelable{
    companion object {

        fun from(value: String) = adapter().fromJson(value) ?: PetLocation()

        fun adapter(): JsonAdapter<PetLocation> = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            .adapter<PetLocation>(PetLocation::class.java)

    }

    override fun toString(): String {
        return adapter().toJson(this)
    }
}

class PetLocationNavType : NavType<PetLocation>(isNullableAllowed = true) {
    override fun get(bundle: Bundle, key: String): PetLocation? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): PetLocation {
        return PetLocation.from(value)
    }

    override fun put(bundle: Bundle, key: String, value: PetLocation) {
        bundle.putParcelable(key, value)
    }
}

object PetLocationBookNavigation {
    const val petLocationArg = "petLocationArg"
    const val route = "petLocationBook?${petLocationArg}={$petLocationArg}"


    fun createRoute(petLocation: PetLocation): String {
        return "petLocationBook?${petLocationArg}=${petLocation.toString()}"
    }

    fun from(entry: NavBackStackEntry) = entry.arguments?.getParcelable<PetLocation>(petLocationArg)

}