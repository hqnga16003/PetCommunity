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

        fun firstWelcome(context: Context){
            val sharedPref = context.getSharedPreferences(
                "welcome",
                Context.MODE_PRIVATE
            )
            val editor = sharedPref.edit()
            editor.apply {
                putBoolean("welcome", true)
                apply()
            }


        }

        fun checkWelcome(context: Context) : Boolean {
            val sharedPref = context.getSharedPreferences(
                "welcome",
                Context.MODE_PRIVATE
            )
            return sharedPref.getBoolean("welcome",false)
        }
    }


}