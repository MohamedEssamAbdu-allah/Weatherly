package com.example.weatherly.model

import com.example.weatherly.utils.SettingsSetup
import com.example.weatherly.utils.Constants
import com.example.weatherly.utils.Converter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

open class WeatherDetails {
    var clouds: Int = 0
    var dt: String = ""
    var humidity: Int = 0
    var pressure: Int = 0
    var temp: String = ""
    var weather: List<Weather> = listOf()
    var windSpeed: String = ""
    var description: String = ""


    companion object {
        fun getDate(timeStamp: Long): String {
            val date = Date(timeStamp * 1000)
            val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
            return dayFormat.format(date)
        }

        fun updateWeatherHourly(hourly: Hourly): WeatherDetails {
            val dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT)
            val formattedDate = dateFormat.format(hourly.dt.toLong() * 1000)
            val weatherDetails = WeatherDetails()
            weatherDetails.temp = when (SettingsSetup.getTempUnits()) {
                Constants.TEMP_FAHRENHEIT_OPTION -> {
                    Converter.kelvinToFahrenheit(hourly.temp)
                }

                Constants.TEMP_CELSIUS_OPTION -> {
                    Converter.kelvinToCelsius(hourly.temp)
                }
                else -> hourly.temp.toString()
            }
            weatherDetails.dt = formattedDate
            weatherDetails.humidity = hourly.humidity
            weatherDetails.weather = hourly.weather
            weatherDetails.pressure = hourly.pressure
            weatherDetails.windSpeed = when (SettingsSetup.getWindSpped()) {
                Constants.MILES_HOUR_OPTION -> {
                    Converter.metersPerSecToMilesPerHr(hourly.wind_speed)
                }
                else -> hourly.wind_speed.toString()
            }
            weatherDetails.description = hourly.weather[0].description
            return weatherDetails
        }

        fun getTodayWeather(current: Current): WeatherDetails {
            val dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT)
            val weatherDetails = WeatherDetails()
            val formattedDate = dateFormat.format(current.dt.toLong() * 1000)
            weatherDetails.temp = when (SettingsSetup.getTempUnits()) {
                Constants.TEMP_FAHRENHEIT_OPTION -> {
                    Converter.kelvinToFahrenheit(current.temp)
                }

                Constants.TEMP_CELSIUS_OPTION -> {
                    Converter.kelvinToCelsius(current.temp)
                }
                else -> current.temp.toString()
            }
            weatherDetails.dt = formattedDate
            weatherDetails.humidity = current.humidity
            weatherDetails.weather = current.weather
            weatherDetails.pressure = current.pressure
            weatherDetails.windSpeed = when (SettingsSetup.getWindSpped()) {
                Constants.MILES_HOUR_OPTION -> {
                    Converter.metersPerSecToMilesPerHr(current.wind_speed)
                }
                else -> current.wind_speed.toString()
            }
            weatherDetails.description = current.weather[0].description
            return weatherDetails
        }

        fun getWeekWeather(daily: Daily): WeatherDetails {
            val dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT)
            val weatherDetails = WeatherDetails()
            val formattedDate = dateFormat.format(daily.dt.toLong() * 1000)
            weatherDetails.temp = when (SettingsSetup.getTempUnits()) {
                Constants.TEMP_FAHRENHEIT_OPTION -> {
                    Converter.kelvinToFahrenheit(daily.temp.max)
                }

                Constants.TEMP_CELSIUS_OPTION -> {
                    Converter.kelvinToCelsius(daily.temp.max)
                }
                else -> daily.temp.max.toString()
            }
            weatherDetails.dt = formattedDate
            weatherDetails.humidity = daily.humidity
            weatherDetails.weather = daily.weather
            weatherDetails.pressure = daily.pressure
            weatherDetails.windSpeed = when (SettingsSetup.getWindSpped()) {
                Constants.MILES_HOUR_OPTION -> {
                    Converter.metersPerSecToMilesPerHr(daily.wind_speed)
                }
                else -> daily.wind_speed.toString()
            }
            weatherDetails.description = daily.weather[0].description
            return weatherDetails
        }
    }


}