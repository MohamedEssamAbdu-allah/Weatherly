package com.example.weatherly.network

import com.example.weatherly.model.Current
import com.example.weatherly.model.Hourly

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

    override suspend fun getHourlyWeather(
        lat: Double,
        lon: Double,
        units: String,
        apiKey: String
    ): List<Hourly> {
        return weatherService.getWeather(lat,lon,units,apiKey).hourly
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