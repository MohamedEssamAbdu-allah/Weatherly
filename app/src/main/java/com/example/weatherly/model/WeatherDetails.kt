package com.example.weatherly.model

import java.text.DateFormat
import java.util.*

open class WeatherDetails {
    var clouds: Int = 0
    var dt: String = ""
    var humidity: Int = 0
    var pressure: Int = 0
    var temp: Double = .0
    var weather: List<Weather> = listOf()
    var windSpeed: Double = .0
    var description: String = ""

    companion object {
        fun getDate() : String{
            val dateFormat = DateFormat.getDateInstance(DateFormat.LONG)
            val calendar = Calendar.getInstance()
            val currentTime = calendar.time
            return dateFormat.format(currentTime)
        }

        fun updateWeatherHourly(hourly: Hourly): WeatherDetails {
            val dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT)
            val formattedDate = dateFormat.format(hourly.dt.toLong()*1000)
            val weatherDetails = WeatherDetails()
            weatherDetails.temp = hourly.temp
            weatherDetails.temp = hourly.temp
            weatherDetails.dt = formattedDate
            weatherDetails.humidity = hourly.humidity
            weatherDetails.weather = hourly.weather
            weatherDetails.pressure = hourly.pressure
            weatherDetails.windSpeed = hourly.wind_speed
            weatherDetails.description = hourly.weather[0].description
            return weatherDetails
        }

        fun initWeatherData(current: Current): WeatherDetails {
            val dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT)
            val formattedDate = dateFormat.format(current.dt.toLong()*1000)
            val weatherDetails = WeatherDetails()
            weatherDetails.temp = current.temp
            weatherDetails.dt = formattedDate
            weatherDetails.humidity = current.humidity
            weatherDetails.weather = current.weather
            weatherDetails.pressure = current.pressure
            weatherDetails.windSpeed = current.wind_speed
            weatherDetails.description = current.weather[0].description
            return weatherDetails
        }
    }
}