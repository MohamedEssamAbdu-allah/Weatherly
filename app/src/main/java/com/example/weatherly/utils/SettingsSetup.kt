package com.example.weatherly.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import java.util.*

object SettingsSetup {

    private lateinit var _sharedPreferences: SharedPreferences
    private lateinit var _tempUnits: String
    private lateinit var _windSpeed: String
    private lateinit var _symbol: String
    private lateinit var _language: String
    private lateinit var _location: String
    private var lat: Double = 0.0
        private set
    private var lon: Double = 0.0
        private set

    fun initialize(sharedPreferences: SharedPreferences) {
        _sharedPreferences = sharedPreferences
        readPrefs()
        Log.i("Settings", " Initialized")
    }

    fun readPrefs() {
        _tempUnits = _sharedPreferences.getString(Constants.TEMP_KEY, "Empty").toString()
        _windSpeed = _sharedPreferences.getString(Constants.WIND_KEY, "Empty").toString()
        _language = _sharedPreferences.getString(Constants.LANG_KEY, "Empty").toString()
        _location = _sharedPreferences.getString(Constants.LOCATION_KEY, "Empty").toString()
        _symbol = _sharedPreferences.getString(Constants.SYMBOL_KEY, "Empty").toString()
    }

     fun setLang(lang: String, context: Context) {
        val config = context.resources.configuration
        val locale = Locale(lang)
        Locale.setDefault(locale)
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    fun setLatitude(latitude: Double) {
        this.lat = latitude
        _sharedPreferences.edit().putString(Constants.LAT_VALUE, lat.toString()).commit()
    }

    fun setLongitude(longitude: Double) {
        this.lon = longitude
        _sharedPreferences.edit().putString(Constants.LON_VALUE, lon.toString()).commit()
    }

    fun getLatitude(): Double {
        return this.lat
    }

    fun getLongitude(): Double {
        return this.lon
    }

    fun getSharedPref(): SharedPreferences {
        return _sharedPreferences
    }

    fun updateSettings(sharedPreferences: SharedPreferences) {
        _sharedPreferences = sharedPreferences
        readPrefs()
        Log.i("Settings update", " UPDATED!")
    }

    fun getTempUnits(): String {
        return _tempUnits
    }

    fun getWindSpped(): String {
        return _windSpeed
    }

    fun getLanguage(): String {
        return _language
    }

    fun getLocation(): String {
        return _location
    }

    fun getSymbol(): String {
        return _symbol
    }


}
