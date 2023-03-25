package com.example.weatherly.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherly.model.RepositoryInterface
import com.example.weatherly.utils.SettingsSetup

class HomeViewModelFactory (private val repo : RepositoryInterface, private val settingsSetup: SettingsSetup) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            HomeViewModel(repo,settingsSetup) as T
        } else {
            throw IllegalArgumentException("HomeViewModel class not found")
        }
    }
}