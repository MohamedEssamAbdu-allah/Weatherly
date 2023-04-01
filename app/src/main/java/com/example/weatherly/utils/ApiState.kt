package com.example.weatherly.utils

import com.example.weatherly.model.WeatherModel

sealed class ApiState {
    class Success(val weatherModel: WeatherModel) : ApiState()
    class Failure(val msg: Throwable) : ApiState()
    object Loading : ApiState()
}