package com.example.weatherly.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherly.utils.Constants

object SettingsSetup2 {

    private lateinit var _sharedPreferences: SharedPreferences
    private lateinit var _tempUnits : String
    private lateinit var _windSpeed : String
    private lateinit var _symbol : String
    private lateinit var _language : String
    private lateinit var _location : String

    fun initialize(sharedPreferences: SharedPreferences){
        this._sharedPreferences = sharedPreferences
        readPrefs()
        Log.i("Settings", " Initialized")
    }

    fun readPrefs(){
         _tempUnits = _sharedPreferences.getString(Constants.TEMP_KEY,"Empty").toString()
        _windSpeed = _sharedPreferences.getString(Constants.WIND_KEY,"Empty").toString()
        _language = _sharedPreferences.getString(Constants.LANG_KEY,"Empty").toString()
        _location = _sharedPreferences.getString(Constants.LOCATION_KEY,"Empty").toString()
        _symbol = _sharedPreferences.getString(Constants.SYMBOL_KEY,"Empty").toString()
    }

    fun getSharedPref() : SharedPreferences{
        return this._sharedPreferences
    }

    fun updateSettings(sharedPreferences: SharedPreferences){
        this._sharedPreferences = sharedPreferences
        readPrefs()
        Log.i("Settings update", " UPDATED!")
    }
    fun getTempUnits() : String{
        return this._tempUnits
    }

    fun getWindSpped() : String{
        return this._windSpeed
    }

    fun getLanguage() : String{
        return this._language
    }

    fun getLocation() : String{
        return this._location
    }

    fun getSymbol() : String{
        return this._symbol
    }


}
