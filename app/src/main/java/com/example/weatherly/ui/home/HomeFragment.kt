package com.example.weatherly.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.weatherly.databinding.FragmentHomeBinding
import com.example.weatherly.model.Hourly
import com.example.weatherly.model.Repository
import com.example.weatherly.model.WeatherDetails
import com.example.weatherly.network.RetrofitClient
import com.example.weatherly.utils.SettingsSetup
import com.example.weatherly.utils.Units


class HomeFragment : Fragment(),HomeClickListener {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var homeViewModelFactory: HomeViewModelFactory
    lateinit var myUnits: Units
    lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myUnits = Units.METRIC
        homeViewModelFactory = HomeViewModelFactory(
            Repository.getInstance(RetrofitClient.getInstance()),
            SettingsSetup.getInstance(myUnits)
        )
        val homeViewModel =
            ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.weekTv.setOnClickListener {
            Navigation.findNavController(root)
                .navigate(HomeFragmentDirections.actionNavHomeToWeekFragment())
        }
        binding.dateTv.text = WeatherDetails.getDate()

        homeViewModel.currentWeather.observe(viewLifecycleOwner) {
            val iconUrl = "https://openweathermap.org/img/wn/${it.weather.get(0).icon}.png"
            Glide.with(requireContext()).load(iconUrl).into(binding.weatherIcon)
            binding.weatherDetailsBinding = WeatherDetails.initWeatherData(it)
            binding.settings = SettingsSetup.getInstance()
        }

        homeViewModel.hourlyWeather.observe(viewLifecycleOwner) {
            homeAdapter = HomeAdapter(requireContext(), it, this)
            binding.hourlyAdapterBinding = homeAdapter
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onHourClicked(hourly: Hourly) {
        val iconUrl = "https://openweathermap.org/img/wn/${hourly.weather.get(0).icon}.png"
        Glide.with(requireContext()).load(iconUrl).into(binding.weatherIcon)
        binding.weatherDetailsBinding = WeatherDetails.updateWeatherHourly(hourly)
    }
}