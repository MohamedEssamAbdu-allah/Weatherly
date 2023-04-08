package com.example.weatherly.model

import com.example.weatherly.network.RemoteSource
import com.example.weatherly.utils.Constants
import com.example.weatherly.utils.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository private constructor(private val remoteSource: RemoteSource) : RepositoryInterface {
    companion object {
        private var instance: Repository? = null
        fun getInstance(remoteSource: RemoteSource): Repository {
            return instance ?: synchronized(this) {
                val temp = Repository(remoteSource)
                instance = temp
                temp
            }
        }
    }

    fun makePref(){

    }

    override suspend fun getWeatherData(units:String): Current {
       return remoteSource.getCurrentWeather(31.104994885376325,29.775266209000975, units, Constants.API_KEY)
    }

    override suspend fun getHourlyWeatherData(units: String): List<Hourly> {
        return remoteSource.getHourlyWeather(31.104994885376325,29.775266209000975, units, Constants.API_KEY)
    }

    override suspend fun getWeatherModelData(units: String): WeatherModel {
        return remoteSource.getWeatherModel(31.104994885376325,29.775266209000975, units, Constants.API_KEY)
    }

    override  fun getFlowWeatherModelData(units: String?): Flow<WeatherModel> {
        return flow { emit(remoteSource.getWeatherModel(Location.lat,Location.lon, units, Constants.API_KEY)) }
    }
}