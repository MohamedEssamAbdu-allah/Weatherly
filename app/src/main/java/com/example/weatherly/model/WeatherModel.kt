package com.example.weatherly.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "weatherModels")
data class WeatherModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    var timezone: String,
    val timezone_offset: Int
) : java.io.Serializable