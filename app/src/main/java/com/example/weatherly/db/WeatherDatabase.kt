package com.example.weatherly.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherly.model.WeatherModel

@Database(entities = [WeatherModel::class], version = 1)
@TypeConverters(RoomConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getWeatherModelDao(): WeatherModelDAO

    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, WeatherDatabase::class.java, "color_database").build()
                INSTANCE = instance
                instance
            }
        }

    }
}