package com.example.weatherly.ui.week

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.weatherly.databinding.FragmentWeekBinding
import com.example.weatherly.model.Daily
import com.example.weatherly.model.Repository
import com.example.weatherly.model.WeatherDetails
import com.example.weatherly.network.RetrofitClient
import com.example.weatherly.utils.ApiState
import com.example.weatherly.utils.SettingsSetup
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class WeekFragment : Fragment() , DayClickListener{
    private lateinit var _binding: FragmentWeekBinding


    private lateinit var weekViewModelFactory: WeekViewModelFactory
    private lateinit var weekAdapter: WeekAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        weekViewModelFactory = WeekViewModelFactory(
            Repository.getInstance(RetrofitClient.getInstance()),
            SettingsSetup.getInstance()
        )
        val weekViewModel =
            ViewModelProvider(this,weekViewModelFactory).get(WeekViewModel::class.java)
        _binding = FragmentWeekBinding.inflate(inflater, container, false)
        lifecycleScope.launch {
            weekViewModel.stateFlow.collectLatest { result ->
                when (result) {
                    is ApiState.Success -> {
                        initUI(result)
                    }
                    is ApiState.Failure -> {
                        Toast.makeText(
                            requireContext(), "Couldn't download data", Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        _binding.weekProgressBar.visibility = View.VISIBLE
                        _binding.scrollView3.visibility = View.GONE
                        Toast.makeText(requireContext(), "Please wait", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        return _binding.root
    }

    override fun onDayClicked(daily: Daily) {
        val iconUrl = "https://openweathermap.org/img/wn/${daily.weather.get(0).icon}.png"
        Glide.with(requireContext()).load(iconUrl).into(_binding.dayWeatherIcon)
        _binding.weatherDayDetailsBinding = WeatherDetails.getWeekWeather(daily)
        _binding.dayDateTv.text = WeatherDetails.getDate(daily.dt.toLong())
    }

    private fun initUI(apiState: ApiState.Success){
        _binding.weekProgressBar.visibility = View.GONE
        _binding.scrollView3.visibility = View.VISIBLE
        val currentIconUrl = "https://openweathermap.org/img/wn/${apiState.weatherModel.daily[0].weather.get(0).icon}.png"
        Glide.with(requireContext()).load(currentIconUrl).into(_binding.dayWeatherIcon)
        _binding.weatherDayDetailsBinding = WeatherDetails.getWeekWeather(apiState.weatherModel.daily[0])
        _binding.dayDateTv.text = WeatherDetails.getDate(apiState.weatherModel.daily[0].dt.toLong())
        _binding.daySettings = SettingsSetup.getInstance()
        weekAdapter = WeekAdapter(requireContext(),apiState.weatherModel.daily,this@WeekFragment)
        _binding.dailyAdapterBinding = weekAdapter
    }

}