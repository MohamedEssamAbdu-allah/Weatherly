package com.example.weatherly.model

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

open class WeatherDetails {
    var city :String =""
    var clouds: Int = 0
    var dt: String = ""
    var humidity: Int = 0
    var pressure: Int = 0
    var temp: Double = .0
    var weather: List<Weather> = listOf()
    var windSpeed: Double = .0
    var description: String = ""

    companion object {
        fun getDate(timeStamp :Long): String {
            val date = Date(timeStamp *1000)
            val dayFormat = SimpleDateFormat("EEEE",Locale.getDefault())
            return dayFormat.format(date)
        }

        fun updateWeatherHourly(hourly: Hourly): WeatherDetails {
            val dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT)
            val formattedDate = dateFormat.format(hourly.dt.toLong() * 1000)
            val weatherDetails = WeatherDetails()
            weatherDetails.temp = hourly.temp
            weatherDetails.dt = formattedDate
            weatherDetails.humidity = hourly.humidity
            weatherDetails.weather = hourly.weather
            weatherDetails.pressure = hourly.pressure
            weatherDetails.windSpeed = hourly.wind_speed
            weatherDetails.description = hourly.weather[0].description
            return weatherDetails
        }

        fun getTodayWeather(current: Current,city :String): WeatherDetails {
            val dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT)
            val weatherDetails = WeatherDetails()
            val formattedDate = dateFormat.format(current.dt.toLong() * 1000)
            weatherDetails.city = city
            weatherDetails.temp = current.temp
            weatherDetails.dt = formattedDate
            weatherDetails.humidity = current.humidity
            weatherDetails.weather = current.weather
            weatherDetails.pressure = current.pressure
            weatherDetails.windSpeed = current.wind_speed
            weatherDetails.description = current.weather[0].description
            return weatherDetails
        }

        fun getWeekWeather(daily: Daily): WeatherDetails {
            val dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT)
            val weatherDetails = WeatherDetails()
            val formattedDate = dateFormat.format(daily.dt.toLong() * 1000)
            weatherDetails.temp = daily.temp.max
            weatherDetails.dt = formattedDate
            weatherDetails.humidity = daily.humidity
            weatherDetails.weather = daily.weather
            weatherDetails.pressure = daily.pressure
            weatherDetails.windSpeed = daily.wind_speed
            weatherDetails.description = daily.weather[0].description
            return weatherDetails
        }
    }
}