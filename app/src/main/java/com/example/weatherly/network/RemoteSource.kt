package com.example.weatherly.network

import com.example.weatherly.model.Current


interface RemoteSource {
    suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        units:String,
        apiKey: String
    ): Current

}