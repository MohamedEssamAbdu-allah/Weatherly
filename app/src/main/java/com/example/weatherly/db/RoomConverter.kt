package com.example.weatherly.db

import androidx.room.TypeConverter
import com.example.weatherly.model.Current
import com.example.weatherly.model.Daily
import com.example.weatherly.model.Hourly
import com.google.gson.Gson

class RoomConverter {
    @TypeConverter
    fun fromCurrentToString(current: Current) = Gson().toJson(current)

    @TypeConverter
    fun fromStringToCurrent(stringCurrent: String) =
        Gson().fromJson(stringCurrent, Current::class.java)

    @TypeConverter
    fun fromDailyListToString(daily: List<Daily>) = Gson().toJson(daily)

    @TypeConverter
    fun fromStringToDailyList(stringDaily: String) =
        Gson().fromJson(stringDaily, Array<Daily>::class.java).toList()

    @TypeConverter
    fun fromHourlyListToString(hourly: List<Hourly>) = Gson().toJson(hourly)

    @TypeConverter
    fun fromStringToHourlyList(stringHourly: String) =
        Gson().fromJson(stringHourly, Array<Hourly>::class.java).toList()

}