package com.example.weatherly.utils

import android.content.SharedPreferences

class SettingsSetup private constructor(sharedPreferences: SharedPreferences? = null) {
    val unit = sharedPreferences?.getString(Constants.TEMP_KEY,"Empty")
    val windSpeedUnit = sharedPreferences?.getString(Constants.WIND_KEY,"Empty")
    val language = sharedPreferences?.getString(Constants.LANG_KEY,"Empty")
    val location = sharedPreferences?.getString(Constants.LOCATION_KEY,"Empty")
    val degreeSymbol = sharedPreferences?.getString(Constants.SYMBOL_KEY,"Empty")


    companion object {
        private var instance: SettingsSetup? = null
        fun getInstance(sharedPreferences: SharedPreferences? = null): SettingsSetup {
            return instance ?: synchronized(this) {
                val temp = SettingsSetup(sharedPreferences)
                instance = temp
                temp
            }
        }
    }

}
