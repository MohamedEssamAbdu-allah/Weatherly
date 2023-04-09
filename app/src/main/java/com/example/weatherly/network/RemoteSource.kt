package com.example.weatherly.network

import com.example.weatherly.model.Current
import com.example.weatherly.model.Hourly
import com.example.weatherly.model.WeatherModel


interface RemoteSource {
    suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        units: String,
        apiKey: String
    ): Current

    suspend fun getHourlyWeather(
        lat: Double,
        lon: Double,
        units: String,
        apiKey: String
    ): List<Hourly>

    suspend fun getWeatherModel(
        lat: Double,
        lon: Double,
        lang: String?,
        apiKey: String
    ) : WeatherModel

}