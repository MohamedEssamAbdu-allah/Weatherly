package com.example.weatherly.model

interface RepositoryInterface {
    suspend fun getWeatherData(units:String) : Current
}