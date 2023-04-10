package com.example.weatherly.ui.map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherly.model.RepositoryInterface
import com.example.weatherly.utils.Constants
import com.example.weatherly.utils.SettingsSetup
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MapViewModel (private val repositoryInterface: RepositoryInterface): ViewModel() {

     fun initMap(){
        val shared = SettingsSetup.getSharedPref()
        shared.getString(Constants.LAT_VALUE, "Empty")
            ?.let { SettingsSetup.setLatitude(it.toDouble())
                println(SettingsSetup.getLatitude().toString() + "init Map")
            }
        shared.getString(Constants.LON_VALUE, "Empty")
            ?.let { SettingsSetup.setLongitude(it.toDouble())
                println(SettingsSetup.getLongitude().toString() + "init Map")
            }
        Log.i("Init map", " initialized")
    }


    fun storeLocation(lat :Double, lon : Double){
        viewModelScope.launch {
            repositoryInterface.getFlowWeatherModelData(lat,lon).collectLatest {
                repositoryInterface.saveLocationWeather(it)
                Log.i("Store Location", "Stored")
            }
        }

    }
}