package com.example.weatherly.db

import com.example.weatherly.model.WeatherModel
import kotlinx.coroutines.flow.Flow

interface LocalSource {
    suspend  fun insertOrUpdate(weatherModel: WeatherModel)
    suspend  fun deleteData(weatherModel: WeatherModel)
    fun getStoredWeatherData() : Flow<List<WeatherModel>>
    fun getStoredLocationData(id : Int) : WeatherModel
}