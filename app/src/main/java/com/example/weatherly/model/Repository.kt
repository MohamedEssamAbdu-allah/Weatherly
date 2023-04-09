package com.example.weatherly.model

import com.example.weatherly.db.LocalSource
import com.example.weatherly.network.RemoteSource
import com.example.weatherly.utils.Constants
import com.example.weatherly.utils.SettingsSetup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository private constructor(private val remoteSource: RemoteSource, private val localSource: LocalSource) : RepositoryInterface {
    companion object {
        private var instance: Repository? = null
        fun getInstance(remoteSource: RemoteSource,localSource: LocalSource): Repository {
            return instance ?: synchronized(this) {
                val temp = Repository(remoteSource,localSource)
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

    override  fun getFlowWeatherModelData(lat :Double,lon:Double): Flow<WeatherModel> {
        return flow { emit(remoteSource.getWeatherModel(lat,lon, SettingsSetup.getLanguage(), Constants.API_KEY)) }
    }

    override fun getLocationsData(): Flow<List<WeatherModel>> {
        return localSource.getStoredWeatherData()
    }

    override fun getOneLocationData(id : Int): WeatherModel {
        return localSource.getStoredLocationData(id)
    }

    override suspend fun saveLocationWeather(weatherModel: WeatherModel) {
        localSource.insertOrUpdate(weatherModel)
    }

    override suspend fun removeLocationWeather(weatherModel: WeatherModel) {
        localSource.deleteData(weatherModel)
    }
}