package com.example.weatherly.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherly.model.RepositoryInterface
import com.example.weatherly.ui.home.HomeViewModel

class MapViewModelFactory (
    private val repo: RepositoryInterface
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            MapViewModel(repo) as T
        } else {
            throw IllegalArgumentException("MapViewModel class not found")
        }
    }
}