package com.example.weatherly.model

import com.example.weatherly.network.RemoteSource

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

    override suspend fun getWeatherData(): Current {
       return remoteSource.getCurrentWeather(31.104994885376325,29.775266209000975,"743e6d5345fcf793390c31100d4e51e0")
    }
}