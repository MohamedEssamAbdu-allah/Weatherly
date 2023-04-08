package com.example.weatherly.utils

object Converter {
    fun celsiusToFahrenheit(celsius: Double): String {
        val fahrenheit = celsius * 1.8 + 32
        return "%.2f".format(fahrenheit)
    }

    fun celsiusToKelvin(celsius: Double): String {
        val kelvin = celsius + 273.15
        return "%.2f".format(kelvin)
    }

    fun metersPerSecToMilesPerHr(metersPerSec: Double): String {
        val milesPerHr = metersPerSec * 2.23694
        return "%.2f".format(milesPerHr)
    }
}