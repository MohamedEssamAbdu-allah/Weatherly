package com.example.weatherly.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherly.model.Current
import com.example.weatherly.model.RepositoryInterface
import com.example.weatherly.utils.SettingsSetup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DateFormat
import java.util.*

class HomeViewModel(
    private val repositoryInterface: RepositoryInterface,
    private val settingsSetup: SettingsSetup
) : ViewModel() {

    private lateinit var calendar: Calendar
    private lateinit var currentTime: Date
    private val timeFormat = DateFormat.getTimeInstance(DateFormat.LONG)
    private val dateFormat = DateFormat.getDateInstance(DateFormat.LONG)
    private lateinit var timeString: String
    private var dateString: String


    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _time = MutableLiveData<String>().apply {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                getTime()
                timeString = timeFormat.format(currentTime)
                delay(1000)
                withContext(Dispatchers.Main) {
                    value = timeString
                }
            }
        }
    }

    private val _date = MutableLiveData<String>().apply {
        getTime()
        dateString = dateFormat.format(currentTime)
        value = dateString
    }

    val time: LiveData<String> = _time
    val date: LiveData<String> = _date

    private fun getTime() {
        calendar = Calendar.getInstance()
        currentTime = calendar.time
    }

    private val _current = MutableLiveData<Current>().apply {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repositoryInterface.getWeatherData(settingsSetup.unit)
            withContext(Dispatchers.Main) {
                value = data
            }
        }
    }
    val currentWeather: LiveData<Current> = _current

}