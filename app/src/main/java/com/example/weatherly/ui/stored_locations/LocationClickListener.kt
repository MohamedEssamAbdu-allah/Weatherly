package com.example.weatherly.ui.stored_locations

import com.example.weatherly.model.WeatherModel

interface LocationClickListener {

    fun showStoredLocation(weatherModel: WeatherModel)
    fun deleteStoredLocation(weatherModel: WeatherModel)
}