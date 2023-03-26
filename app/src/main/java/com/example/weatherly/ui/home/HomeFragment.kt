package com.example.weatherly.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.weatherly.R
import com.example.weatherly.databinding.FragmentHomeBinding
import com.example.weatherly.model.Repository
import com.example.weatherly.utils.Units
import com.example.weatherly.network.RetrofitClient
import com.example.weatherly.utils.SettingsSetup


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


        homeViewModel.time.observe(viewLifecycleOwner) {
            binding.timeTv.text = it
        }
        homeViewModel.date.observe(viewLifecycleOwner) {
            binding.dateTv.text = it
        }

        homeViewModel.currentWeather.observe(viewLifecycleOwner) {
            val iconUrl = "https://openweathermap.org/img/wn/${it.weather.get(0).icon}.png"
            binding.temperatureTv.text = getString(
                R.string.temperature_tv,
                it.temp,
                SettingsSetup.getInstance().degreeSymbol
            )
            binding.humidityTv.text = getString(R.string.humidity_tv, it.humidity, "%")
            binding.cloudTv.text = getString(R.string.cloud_tv, it.clouds, "%")
            binding.pressureTv.text = getString(R.string.pressure_tv, it.pressure, " hPa")
            binding.windSpeedTv.text = getString(R.string.windSpeed_tv, it.wind_speed, SettingsSetup.getInstance().windSpeed)
            binding.wetherDesc.text = it.weather.get(0).description
            Glide.with(requireContext()).load(iconUrl).into(binding.weatherIcon)
        }

        homeViewModel.hourlyWeather.observe(viewLifecycleOwner){
            homeAdapter = HomeAdapter(requireContext(),it,this)
            binding.hourlyRecView.adapter = homeAdapter
        }
        //val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            binding.timeTv.text = it
//        }

        //unixTimeStamp Conversion
//        val unixTimestamp = 1679717068L // Unix timestamp in seconds
//        val date = Date(unixTimestamp * 1000) // Create a new Date object with the Unix timestamp in milliseconds
//        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) // Define the date format
//        val formattedDate = dateFormat.format(date)
//        println(formattedDate)


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}