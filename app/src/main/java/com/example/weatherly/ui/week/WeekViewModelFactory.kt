package com.example.weatherly.ui.week

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherly.model.RepositoryInterface
import com.example.weatherly.utils.SettingsSetup

class WeekViewModelFactory(
    private val repo: RepositoryInterface, private val settingsSetup: SettingsSetup
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(WeekViewModel::class.java)) {
            WeekViewModel(repo, settingsSetup) as T
        } else {
            throw IllegalArgumentException("HomeViewModel class not found")
        }
    }
}