package com.example.weatherly.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherly.model.Current
import com.example.weatherly.model.Hourly
import com.example.weatherly.model.RepositoryInterface
import com.example.weatherly.model.WeatherModel
import com.example.weatherly.utils.SettingsSetup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val repositoryInterface: RepositoryInterface,
    private val settingsSetup: SettingsSetup
) : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _current = MutableLiveData<Current>().apply {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repositoryInterface.getWeatherData(settingsSetup.unit)
            withContext(Dispatchers.Main) {
                value = data
            }
        }
    }
    val currentWeather: LiveData<Current> = _current

    private val _hourly = MutableLiveData<List<Hourly>>().apply {
        viewModelScope.launch(Dispatchers.IO){
            val data = repositoryInterface.getHourlyWeatherData(settingsSetup.unit)
            withContext(Dispatchers.Main){
                value = data
            }
        }
    }
    val hourlyWeather : LiveData<List<Hourly>> = _hourly

    private val _weatherModel = MutableLiveData<WeatherModel>().apply {
        viewModelScope.launch(Dispatchers.IO){
            val data = repositoryInterface.getWeatherModelData(settingsSetup.unit)
            withContext(Dispatchers.Main){
                value = data
            }
        }
    }
        val weatherDetails :LiveData<WeatherModel> = _weatherModel
}