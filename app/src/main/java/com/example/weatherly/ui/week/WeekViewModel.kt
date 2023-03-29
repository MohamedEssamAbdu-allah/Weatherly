package com.example.weatherly.ui.week

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherly.model.RepositoryInterface
import com.example.weatherly.model.WeatherModel
import com.example.weatherly.utils.SettingsSetup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeekViewModel(
    private val repositoryInterface: RepositoryInterface,
    private val settingsSetup: SettingsSetup
) : ViewModel() {

    private val _weatherModel = MutableLiveData<WeatherModel>().apply {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repositoryInterface.getWeatherModelData(settingsSetup.unit)
            withContext(Dispatchers.Main) {
                value = data
            }
        }
    }

    val weatherDetails: LiveData<WeatherModel> = _weatherModel


}