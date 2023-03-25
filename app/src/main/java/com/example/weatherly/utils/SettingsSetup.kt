package com.example.weatherly.utils

class SettingsSetup private constructor(units: Units) {
    val degreeSymbol = when (units) {
        Units.METRIC -> Constants.CELSIUS
        Units.IMPERIAL -> Constants.FAHRENHEIT
        else -> Constants.KELVIN
    }
    val unit = when (units) {
        Units.METRIC -> Constants.UNITS_METRIC
        Units.IMPERIAL -> Constants.UNITS_IMPERIAL
        else -> Constants.UNITS_STANDARD
    }

    val windSpeed = when(units){
        Units.METRIC -> Constants.METER_SEC
        Units.IMPERIAL -> Constants.MILES_HOUR
        else -> Constants.METER_SEC
    }

    companion object {
        private var instance: SettingsSetup? = null
        fun getInstance(units: Units= Units.STANDARD): SettingsSetup {
            return instance ?: synchronized(this) {
                val temp = SettingsSetup(units)
                instance = temp
                temp
            }
        }
    }

}
