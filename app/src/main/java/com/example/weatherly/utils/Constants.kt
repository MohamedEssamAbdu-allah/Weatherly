package com.example.weatherly.utils

object Constants {
    const val CELSIUS = "\u2103"
    const val FAHRENHEIT = "\u2109"
    const val KELVIN = "\u212A"
    const val API_KEY = "743e6d5345fcf793390c31100d4e51e0"
    const val UNITS_METRIC = "metric"
    const val UNITS_IMPERIAL = "imperial"
    const val UNITS_STANDARD = "standard"
    const val METER_SEC = "metre/sec"
    const val MILES_HOUR = "miles/hr"
}

enum class Units {
     METRIC,
     IMPERIAL,
     STANDARD,
}