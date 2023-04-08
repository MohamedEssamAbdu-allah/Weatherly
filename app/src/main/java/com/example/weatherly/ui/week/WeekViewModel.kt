package com.example.weatherly.ui.week

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherly.model.RepositoryInterface
import com.example.weatherly.utils.ApiState
import com.example.weatherly.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class WeekViewModel(
    private val repositoryInterface: RepositoryInterface
) : ViewModel() {
    val stateFlow = MutableStateFlow<ApiState>(ApiState.Loading)
    init {
        getStateFlowProducts()
    }
    private fun getStateFlowProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryInterface.getFlowWeatherModelData(Constants.TEMP_CELSIUS_OPTION)
                .catch { e -> stateFlow.value = ApiState.Failure(e) }.collect { data ->
                    stateFlow.value = ApiState.Success(data)
                }
        }
    }


}