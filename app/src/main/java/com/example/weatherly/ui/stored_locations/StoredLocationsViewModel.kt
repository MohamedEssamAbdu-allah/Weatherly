package com.example.weatherly.ui.stored_locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherly.model.RepositoryInterface
import com.example.weatherly.model.WeatherModel
import com.example.weatherly.utils.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StoredLocationsViewModel(private val repositoryInterface: RepositoryInterface) : ViewModel() {

    private var privateStoredLocations: MutableLiveData<List<WeatherModel>> = MutableLiveData<List<WeatherModel>>()
    val storedLocations : LiveData<List<WeatherModel>> = privateStoredLocations


    init {
        getStoredLocationsData()
    }
    private fun getStoredLocationsData(){
        viewModelScope.launch(Dispatchers.IO){
            repositoryInterface.getLocationsData().collect{
                privateStoredLocations.postValue(it)
            }
        }
    }

     fun deleteStoredLocation(weatherModel: WeatherModel){
        viewModelScope.launch {
            repositoryInterface.removeLocationWeather(weatherModel)
        }
    }

//    private fun updateStoredData(){
//        viewModelScope.launch(Dispatchers.IO){
//            getStoredLocationsData()
//            val oldList = privateStoredLocations.value
//            oldList?.forEach {
//                repositoryInterface.getFlowWeatherModelData(it.lat,it.lon)
//                    .catch { e -> stateFlow.value = ApiState.Failure(e) }.collect{
//                        data -> stateFlow.value = ApiState.Success(data)
//                    }
//            }
//            privateStoredLocations.value = stateFlow.value
//        }
//    }
}