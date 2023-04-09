package com.example.weatherly.model

import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {
    suspend fun getWeatherData(units: String): Current
    suspend fun getHourlyWeatherData(units: String): List<Hourly>
//    suspend fun getWeatherModelData(units: String): WeatherModel
    fun getFlowWeatherModelData(lat:Double,lon:Double): Flow<WeatherModel>
    fun getLocationsData(): Flow<List<WeatherModel>>
    suspend fun saveLocationWeather(weatherModel: WeatherModel)
    suspend fun removeLocationWeather(weatherModel: WeatherModel)

}