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


    private val binding get() = _binding!!
    lateinit var homeViewModelFactory: HomeViewModelFactory
    lateinit var myUnits: Units
    lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myUnits = Units.IMPERIAL
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


        homeViewModel.weatherDetails.observe(viewLifecycleOwner){
            val currentIconUrl = "https://openweathermap.org/img/wn/${it.current.weather.get(0).icon}.png"
            Glide.with(requireContext()).load(currentIconUrl).into(binding.weatherIcon)
            binding.weatherDetailsBinding = WeatherDetails.getTodayWeather(it.current)
            binding.dateTv.text = WeatherDetails.getDate(it.current.dt.toLong())
            binding.settings = SettingsSetup.getInstance()
            homeAdapter = HomeAdapter(requireContext(),it.hourly,this)
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