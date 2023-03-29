package com.example.weatherly.model

interface RepositoryInterface {
    suspend fun getWeatherData(units:String) : Current
    suspend fun getHourlyWeatherData(units:String) : List<Hourly>
    suspend fun getWeatherModelData(units:String) : WeatherModel
}