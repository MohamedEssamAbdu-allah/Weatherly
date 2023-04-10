package com.example.weatherly.ui.location_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherly.model.RepositoryInterface

class LocationDetailsFactory (
    private val repo: RepositoryInterface
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LocationDetailsViewModel::class.java)) {
            LocationDetailsViewModel(repo) as T
        } else {
            throw IllegalArgumentException("HomeViewModel class not found")
        }
    }
}