package com.example.weatherly.model

import kotlinx.coroutines.flow.Flow

class FakeRepository : RepositoryInterface {



    override suspend fun getWeatherData(units: String): Current {
        TODO("Not yet implemented")
    }

    override suspend fun getHourlyWeatherData(units: String): List<Hourly> {
        TODO("Not yet implemented")
    }

    override fun getFlowWeatherModelData(lat: Double, lon: Double): Flow<WeatherModel> {
        TODO("Not yet implemented")
    }

    override fun getLocationsData(): Flow<List<WeatherModel>> {
        TODO("Not yet implemented")
    }

    override fun getOneLocationData(id: Int): WeatherModel {
        TODO("Not yet implemented")
    }

    override suspend fun saveLocationWeather(weatherModel: WeatherModel) {
        TODO("Not yet implemented")
    }

    override suspend fun removeLocationWeather(weatherModel: WeatherModel) {
        TODO("Not yet implemented")
    }

}