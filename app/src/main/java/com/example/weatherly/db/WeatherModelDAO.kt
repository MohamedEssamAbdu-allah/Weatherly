package com.example.weatherly.db

import androidx.room.*
import com.example.weatherly.model.WeatherModel
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherModelDAO {

    @Query("SELECT * FROM weatherModels")
    fun getAll(): Flow<List<WeatherModel>>

    @Upsert
    suspend fun insertOrUpdate(weatherModel: WeatherModel)

    @Delete
    suspend fun delete(weatherModel: WeatherModel)
}