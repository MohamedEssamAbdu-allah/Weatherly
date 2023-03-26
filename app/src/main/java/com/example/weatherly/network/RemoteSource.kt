package com.example.weatherly.network

import com.example.weatherly.model.Current
import com.example.weatherly.model.Hourly


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

}