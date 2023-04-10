package com.example.weatherly.ui.stored_locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherly.model.RepositoryInterface
import com.example.weatherly.ui.week.WeekViewModel

class StoredLocationsFactory(
    private val repo: RepositoryInterface
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(StoredLocationsViewModel::class.java)) {
            StoredLocationsViewModel(repo) as T
        } else {
            throw IllegalArgumentException("StoredLocationsViewModel class not found")
        }
    }
}