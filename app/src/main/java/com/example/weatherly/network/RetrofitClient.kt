package com.example.weatherly.network

import com.example.weatherly.model.Current

class RetrofitClient private constructor() : RemoteSource {

    private val weatherService: WeatherService by lazy {
        RetrofitHelper.getInstance().create(
            WeatherService::class.java
        )
    }

    override suspend fun getCurrentWeather(
        lat: Double, lon: Double,units:String, apiKey: String
    ): Current {
        return weatherService.getWeather(lat, lon, units,apiKey).current
    }


    companion object {
        private var instance: RetrofitClient? = null
        fun getInstance(): RetrofitClient {
            return instance ?: synchronized(this) {
                val temp = RetrofitClient()
                instance = temp
                temp
            }
        }
    }
}