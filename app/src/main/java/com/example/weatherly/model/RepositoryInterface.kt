package com.example.weatherly.model

import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {
    suspend fun getWeatherData(units:String) : Current
    suspend fun getHourlyWeatherData(units:String) : List<Hourly>
    suspend fun getWeatherModelData(units:String) : WeatherModel

    fun getFlowWeatherModelData(): Flow<WeatherModel>
}