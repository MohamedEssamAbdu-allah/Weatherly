package com.example.weatherly.utils

object Converter {
    fun kelvinToCelsius(kelvin: Double): String {
        val celsius = kelvin - 273.15
        return "%.1f".format(celsius)
    }
    fun kelvinToFahrenheit(kelvin: Double): String {
        val fahrenheit = (kelvin - 273.15) * 9/5 + 32
        return "%.2f".format(fahrenheit)
    }

    fun metersPerSecToMilesPerHr(metersPerSec: Double): String {
        val milesPerHr = metersPerSec * 2.23694
        return "%.2f".format(milesPerHr)
    }




}