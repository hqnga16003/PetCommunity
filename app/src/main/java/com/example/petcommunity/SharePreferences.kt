package com.example.petcommunity

import android.content.Context
import android.util.Log

class SharePreferences {
    companion object {
        fun getSharePreferences(context: Context) : String {
            val sharedPref = context.getSharedPreferences(
                R.string.preference_myAccount.toString(),
                Context.MODE_PRIVATE
            )
            return sharedPref.getString("uid","").toString()
        }
    }
}