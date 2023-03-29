package com.example.weatherly.ui.week

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weatherly.databinding.FragmentWeekBinding
import com.example.weatherly.model.Daily
import com.example.weatherly.model.Repository
import com.example.weatherly.model.WeatherDetails
import com.example.weatherly.network.RetrofitClient
import com.example.weatherly.utils.SettingsSetup


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



        weekViewModel.weatherDetails.observe(viewLifecycleOwner){
            val currentIconUrl = "https://openweathermap.org/img/wn/${it.current.weather.get(0).icon}.png"
            Glide.with(requireContext()).load(currentIconUrl).into(_binding.dayWeatherIcon)
            _binding.weatherDayDetailsBinding = WeatherDetails.getWeekWeather(it.daily[0])
            _binding.dayDateTv.text = WeatherDetails.getDate(it.daily[0].dt.toLong())
            _binding.daySettings = SettingsSetup.getInstance()
            weekAdapter = WeekAdapter(requireContext(),it.daily,this)
            _binding.dailyAdapterBinding = weekAdapter
        }

        return _binding.root
    }

    override fun onDayClicked(daily: Daily) {
        val iconUrl = "https://openweathermap.org/img/wn/${daily.weather.get(0).icon}.png"
        Glide.with(requireContext()).load(iconUrl).into(_binding.dayWeatherIcon)
        _binding.weatherDayDetailsBinding = WeatherDetails.getWeekWeather(daily)
        _binding.dayDateTv.text = WeatherDetails.getDate(daily.dt.toLong())
    }

}