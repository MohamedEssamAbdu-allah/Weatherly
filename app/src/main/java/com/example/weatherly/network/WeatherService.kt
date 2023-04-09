package com.example.weatherly.network

import com.example.weatherly.model.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("onecall")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("lang") lang :String?,
        @Query("appid") apiKey: String
    ): WeatherModel
}