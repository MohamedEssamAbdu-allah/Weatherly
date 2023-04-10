package com.example.weatherly.ui.location_details

import android.content.Context
import android.location.Geocoder
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.weatherly.R
import com.example.weatherly.databinding.FragmentLocationDetailsBinding
import com.example.weatherly.db.ConcreteLocalSource
import com.example.weatherly.model.Hourly
import com.example.weatherly.model.Repository
import com.example.weatherly.model.WeatherDetails
import com.example.weatherly.model.WeatherModel
import com.example.weatherly.network.RetrofitClient
import com.example.weatherly.ui.home.HomeAdapter
import com.example.weatherly.ui.home.HomeClickListener
import com.example.weatherly.utils.ApiState
import com.example.weatherly.utils.Constants
import com.example.weatherly.utils.SettingsSetup
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

class LocationDetailsFragment : Fragment(), HomeClickListener {

    private lateinit var viewModel: LocationDetailsViewModel
    private lateinit var viewModelFactory: LocationDetailsFactory
    private lateinit var weatherModel: WeatherModel
    private lateinit var _binding: FragmentLocationDetailsBinding
    lateinit var detailsAdapter: HomeAdapter
    lateinit var geoCoder : Geocoder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationDetailsBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: LocationDetailsFragmentArgs =
            LocationDetailsFragmentArgs.fromBundle(requireArguments())
        weatherModel = args.weatherLocation

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelFactory = LocationDetailsFactory(
            Repository.getInstance(
                RetrofitClient.getInstance(),
                ConcreteLocalSource.getInstance(requireContext())
            )
        )
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(LocationDetailsViewModel::class.java)



        if(isNetworkConnected(requireContext())) {
            updateFromNetwork(weatherModel)
        } else {
            viewModel.getStoredLocationsData(weatherModel.id)
            viewModel.storedLocations.observe(viewLifecycleOwner){
                initUI(it)
                Toast.makeText(requireContext(),"Check your network connection to retrieve the most recent forecasts",Toast.LENGTH_LONG).show()
            }
        }
    }

    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private fun updateFromNetwork(model: WeatherModel){
        viewModel.updateLocationDetails(model)
        lifecycleScope.launch {
            viewModel.stateFlow.collectLatest { result ->
                when (result) {
                    is ApiState.Success -> {
                        initUI(result.weatherModel)
                        Toast.makeText(requireContext(),"Updated weather",Toast.LENGTH_SHORT).show()
                    }
                    is ApiState.Failure -> {
                        viewModel.getStoredLocationsData(weatherModel.id)
                        viewModel.storedLocations.observe(viewLifecycleOwner){
                            initUI(it)
                            Toast.makeText(requireContext(),"Couldn't update data, Retrieved from database",Toast.LENGTH_SHORT).show()
                        }
                        Toast.makeText(
                            requireContext(), "Couldn't update data", Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        hideUI()
                    }
                }
            }
        }
    }

    private fun hideUI(){
        _binding.storedHomeProgressBar.visibility = View.VISIBLE
        _binding.locationDetailsScroll.visibility = View.GONE
    }

    private fun showUI(){
        _binding.storedHomeProgressBar.visibility = View.GONE
        _binding.locationDetailsScroll.visibility = View.VISIBLE
    }

    private fun initUI(weatherModel: WeatherModel){
        showUI()
        val currentIconUrl = "https://openweathermap.org/img/wn/${
            weatherModel.current.weather.get(0).icon
        }.png"
        Glide.with(requireContext()).load(currentIconUrl).into(_binding.storedWeatherIcon)
        geoCoder = Geocoder(requireContext(), Locale.getDefault())
        val addresses = geoCoder.getFromLocation(
            weatherModel.lat,
            weatherModel.lon,1)
        if (addresses != null) {
            _binding.storedLocationCity = addresses.get(0).adminArea ?: "Unkown"
        }
        _binding.storedLocationSymbol = SettingsSetup.getSymbol()
        _binding.storedLocationWind = when(SettingsSetup.getWindSpped()){
            Constants.METER_SEC_OPTION -> resources.getString(R.string.meter_option)
            else -> resources.getString(R.string.miles_option)
        }
        _binding.storedLocationDetails = WeatherDetails.getTodayWeather(weatherModel.current)
        _binding.storedDateTv.text = WeatherDetails.getDate(weatherModel.current.dt.toLong())
        detailsAdapter = HomeAdapter(
            requireContext(), weatherModel.hourly, this@LocationDetailsFragment
        )
        _binding.storedLocationAdapter = detailsAdapter
    }

    override fun onHourClicked(hourly: Hourly) {
        val iconUrl = "https://openweathermap.org/img/wn/${hourly.weather.get(0).icon}.png"
        Glide.with(requireContext()).load(iconUrl).into(_binding.storedWeatherIcon)
        _binding.storedLocationDetails = WeatherDetails.updateWeatherHourly(hourly)
    }
}