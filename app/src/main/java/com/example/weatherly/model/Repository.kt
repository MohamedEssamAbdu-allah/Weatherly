package com.example.weatherly.model

import com.example.weatherly.network.RemoteSource
import com.example.weatherly.utils.Constants

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

    override suspend fun getWeatherData(units:String): Current {
       return remoteSource.getCurrentWeather(31.104994885376325,29.775266209000975, units, Constants.API_KEY)
    }

    override suspend fun getHourlyWeatherData(units: String): List<Hourly> {
        return remoteSource.getHourlyWeather(31.104994885376325,29.775266209000975, units, Constants.API_KEY)
    }
}