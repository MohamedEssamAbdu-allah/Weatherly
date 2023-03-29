package com.example.weatherly.ui.home

import com.example.weatherly.model.Hourly

interface HomeClickListener {
    fun onHourClicked(hourly: Hourly)
}