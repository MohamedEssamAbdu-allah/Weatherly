package com.example.weatherly.ui.location_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherly.model.RepositoryInterface
import com.example.weatherly.model.WeatherModel
import com.example.weatherly.utils.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class LocationDetailsViewModel(private val repositoryInterface: RepositoryInterface) : ViewModel() {

    val stateFlow = MutableStateFlow<ApiState>(ApiState.Loading)
    private var privateStoredLocations: MutableLiveData<WeatherModel> =
        MutableLiveData<WeatherModel>()
    val storedLocations: LiveData<WeatherModel> = privateStoredLocations



    fun updateLocationDetails(weatherModel: WeatherModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryInterface.getFlowWeatherModelData(weatherModel.lat, weatherModel.lon)
                .catch { e -> stateFlow.value = ApiState.Failure(e) }.collect { data ->
                    stateFlow.value = ApiState.Success(data)
                    //repositoryInterface.saveLocationWeather(data)
                }
        }
    }

     fun getStoredLocationsData(id : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            privateStoredLocations.postValue(repositoryInterface.getOneLocationData(id))
        }
    }
}