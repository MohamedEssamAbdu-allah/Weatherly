package com.example.weatherly.ui.home

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.weatherly.R
import com.example.weatherly.databinding.FragmentHomeBinding
import com.example.weatherly.db.ConcreteLocalSource
import com.example.weatherly.model.Hourly
import com.example.weatherly.model.Repository
import com.example.weatherly.model.WeatherDetails
import com.example.weatherly.network.RetrofitClient
import com.example.weatherly.utils.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*


class HomeFragment : Fragment(), HomeClickListener {

    private var _binding: FragmentHomeBinding? = null
    lateinit var geoCoder : Geocoder
    private val binding get() = _binding!!
    lateinit var homeViewModelFactory: HomeViewModelFactory
    lateinit var homeAdapter: HomeAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        homeViewModelFactory = HomeViewModelFactory(
            Repository.getInstance(RetrofitClient.getInstance(),ConcreteLocalSource.getInstance(requireContext()))
        )
        val homeViewModel =
            ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.weekTv.setOnClickListener {
            Navigation.findNavController(root)
                .navigate(HomeFragmentDirections.actionNavHomeToWeekFragment())
        }

        lifecycleScope.launch {
            homeViewModel.stateFlow.collectLatest { result ->
                when (result) {
                    is ApiState.Success -> {
                        initUI(result)
                    }
                    is ApiState.Failure -> {
                        binding.homeProgressBar.visibility = View.GONE
                        binding.scrollView2.visibility = View.GONE
                        binding.failedImage.visibility = View.VISIBLE
                        binding.failedText.visibility = View.VISIBLE
                        Toast.makeText(
                            requireContext(), "Couldn't download data", Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        binding.homeProgressBar.visibility = View.VISIBLE
                        binding.scrollView2.visibility = View.GONE
                        binding.failedImage.visibility = View.GONE
                        binding.failedText.visibility = View.GONE
                        Toast.makeText(requireContext(), "Please wait", Toast.LENGTH_SHORT).show()
                    }
                }
            }
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

    private fun initUI(apiState: ApiState.Success) {
        binding.homeProgressBar.visibility = View.GONE
        binding.failedImage.visibility = View.GONE
        binding.failedText.visibility = View.GONE
        binding.scrollView2.visibility = View.VISIBLE
        val currentIconUrl = "https://openweathermap.org/img/wn/${
            apiState.weatherModel.current.weather.get(0).icon
        }.png"
        Glide.with(requireContext()).load(currentIconUrl).into(binding.weatherIcon)
        geoCoder = Geocoder(requireContext(), Locale.getDefault())
       val addresses = geoCoder.getFromLocation(SettingsSetup.getLatitude(),SettingsSetup.getLongitude(),1)
        if (addresses != null) {
            binding.bindingCity = addresses.get(0).adminArea ?: "Unkown"
        }
        binding.bindingSymbol = SettingsSetup.getSymbol()
        binding.bindingWindSpeed = when(SettingsSetup.getWindSpped()){
            Constants.METER_SEC_OPTION -> resources.getString(R.string.meter_option)
            else -> resources.getString(R.string.miles_option)
        }
        binding.weatherDetailsBinding = WeatherDetails.getTodayWeather(apiState.weatherModel.current)
        binding.dateTv.text = WeatherDetails.getDate(apiState.weatherModel.current.dt.toLong())
        homeAdapter = HomeAdapter(
            requireContext(), apiState.weatherModel.hourly, this@HomeFragment
        )
        binding.hourlyAdapterBinding = homeAdapter
    }
}