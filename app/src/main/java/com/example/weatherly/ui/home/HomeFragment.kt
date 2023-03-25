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
import com.example.weatherly.model.Constants
import com.example.weatherly.model.Repository
import com.example.weatherly.network.RetrofitClient


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var homeViewModelFactory: HomeViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModelFactory = HomeViewModelFactory(Repository.getInstance(RetrofitClient.getInstance()))
        val homeViewModel =
            ViewModelProvider(this,homeViewModelFactory).get(HomeViewModel::class.java)
        /*
        NOTE UNICODES FOR WEATHER PREFERENCES
        Celsius = \u2103
        Fahrenheit = \u2109
         */

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.weekTv.setOnClickListener {
            Navigation.findNavController(root)
                .navigate(HomeFragmentDirections.actionNavHomeToWeekFragment())
        }


        homeViewModel.time.observe(viewLifecycleOwner) {
            binding.timeTv.text = it
        }
        homeViewModel.date.observe(viewLifecycleOwner){
            binding.dateTv.text = it
        }

        homeViewModel.currentWeather.observe(viewLifecycleOwner){
            val iconUrl ="https://openweathermap.org/img/wn/${it.weather.get(0).icon}.png"
            binding.temperatureTv.text = getString(R.string.temperature_tv,it.temp,Constants.CELSIUS)
            binding.humidityTv.text = getString(R.string.humidity_tv,it.humidity,"%")
            binding.cloudTv.text = getString(R.string.cloud_tv,it.clouds,"%")
            binding.pressureTv.text = getString(R.string.pressure_tv,it.pressure,"hpa")
            binding.windSpeedTv.text = getString(R.string.windSpeed_tv,it.wind_speed,"/sec")
            binding.wetherDesc.text = it.weather.get(0).description
            Glide.with(requireContext()).load(iconUrl).into(binding.weatherIcon)
        }

        homeViewModel.currentWeather.observe(viewLifecycleOwner){


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