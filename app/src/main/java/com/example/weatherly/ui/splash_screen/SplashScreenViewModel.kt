package com.example.weatherly.ui.splash_screen

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.weatherly.utils.SettingsSetup
import com.example.weatherly.utils.Constants

class SplashScreenViewModel : ViewModel() {

    private fun isSharedPref(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        Log.i("IsSharedPref", sharedPreferences.all.isNullOrEmpty().toString())
        return !sharedPreferences.all.isNullOrEmpty()
    }

    private fun createDefaultSettings(context: Context): SharedPreferences {
        val defaultSharedPref = context.getSharedPreferences(
            Constants.SHARED_PREF_KEY, AppCompatActivity.MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor = defaultSharedPref.edit()
        editor.putString(Constants.TEMP_KEY, Constants.TEMP_KELVIN_OPTION)
        editor.putString(Constants.WIND_KEY, Constants.METER_SEC_OPTION)
        editor.putString(Constants.LANG_KEY, Constants.LANG_ENG_OPTION)
        editor.putString(Constants.LOCATION_KEY, Constants.GPS_OPTION)
        editor.putString(Constants.SYMBOL_KEY, Constants.KELVIN)
        editor.putString(Constants.LAT_VALUE,"0.5")
        editor.putString(Constants.LON_VALUE,"0.10")
        editor.commit()
        Log.i("Default", "default shared pref created")
        return defaultSharedPref
    }

    fun initSettings(context: Context) {
        val sharedPreferences: SharedPreferences
        if (!isSharedPref(context)) {
            sharedPreferences = createDefaultSettings(context)
        } else {
            sharedPreferences = context.getSharedPreferences(
                Constants.SHARED_PREF_KEY, Context.MODE_PRIVATE
            )
            Log.i(
                "settings restored",
                sharedPreferences.getString(Constants.LOCATION_KEY, "Empty").toString()
            )
        }
        SettingsSetup.initialize(sharedPreferences)

    }

}