package com.example.weatherly.ui.week

import com.example.weatherly.model.Daily

interface DayClickListener {
    fun onDayClicked(daily: Daily)
}