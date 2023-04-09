package com.example.weatherly.db

import android.content.Context
import com.example.weatherly.model.WeatherModel
import kotlinx.coroutines.flow.Flow

class ConcreteLocalSource private constructor(context: Context) : LocalSource{

    companion object {
        private var instance: ConcreteLocalSource? = null
        fun getInstance(context: Context): ConcreteLocalSource {
            return instance ?: synchronized(this) {
                val temp = ConcreteLocalSource(context)
                instance = temp
                temp
            }
        }
    }
    private val dao: WeatherModelDAO by lazy {
        val db: WeatherDatabase = WeatherDatabase.getInstance(context)
        db.getWeatherModelDao()
    }

    override suspend fun insertOrUpdate(weatherModel: WeatherModel) {
        dao.insertOrUpdate(weatherModel)
    }

    override suspend fun deleteData(weatherModel: WeatherModel) {
        dao.delete(weatherModel)
    }

    override  fun getStoredWeatherData(): Flow<List<WeatherModel>> {
        return dao.getAll()
    }


}