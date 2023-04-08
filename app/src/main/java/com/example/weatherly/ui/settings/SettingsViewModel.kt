package com.example.weatherly.ui.settings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.weatherly.utils.Constants
import com.example.weatherly.utils.SettingsSetup
import java.util.*


class SettingsViewModel : ViewModel() {

    private val _sharedPreference = SettingsSetup.getSharedPref()
    private val editor: SharedPreferences.Editor = _sharedPreference.edit()

    fun setMetric() {
        editor.putString(Constants.TEMP_KEY, Constants.TEMP_CELSIUS_OPTION)
        editor.putString(Constants.SYMBOL_KEY,Constants.CELSIUS)
    }

    fun setStandard() {
        editor.putString(Constants.TEMP_KEY, Constants.TEMP_KELVIN_OPTION)
        editor.putString(Constants.SYMBOL_KEY,Constants.KELVIN)
    }

    fun setImperial() {
        editor.putString(Constants.TEMP_KEY, Constants.TEMP_FAHRENHEIT_OPTION)
        editor.putString(Constants.SYMBOL_KEY,Constants.FAHRENHEIT)
    }

    fun setMeter() {
        editor.putString(Constants.WIND_KEY, Constants.METER_SEC_OPTION)
    }

    fun setMile() {
        editor.putString(Constants.WIND_KEY, Constants.MILES_HOUR_OPTION)
    }

    fun setArabic(context: Context) {
        editor.putString(Constants.LANG_KEY, Constants.LANG_AR_OPTION)
        changeLang("ar",context)
    }

    fun setEnglish(context: Context) {
        editor.putString(Constants.LANG_KEY, Constants.LANG_ENG_OPTION)
        changeLang("en",context)
    }

    fun setMap() {
        editor.putString(Constants.LOCATION_KEY, Constants.MAP_OPTION)
    }

    fun SetGPS() {
        editor.putString(Constants.LOCATION_KEY, Constants.GPS_OPTION)
    }

    fun saveChanges() {
        editor.commit()
        SettingsSetup.updateSettings(_sharedPreference)
    }

    private fun changeLang(lang: String, context: Context) {
        val config = context.resources.configuration
        val locale = Locale(lang)
        Locale.setDefault(locale)
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
        context.startActivity(Intent(context, context::class.java))
    }

}