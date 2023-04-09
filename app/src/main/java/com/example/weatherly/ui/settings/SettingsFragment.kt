package com.example.weatherly.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.weatherly.R
import com.example.weatherly.databinding.FragmentSettingsBinding
import com.example.weatherly.utils.Constants
import com.example.weatherly.utils.SettingsSetup

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        sharedPreferences = SettingsSetup.getSharedPref()



        setupRadioButtons()
        changeSettings()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupRadioButtons() {
        binding.windSpeedGroup.check(
            when (SettingsSetup.getWindSpped()) {
                Constants.METER_SEC_OPTION -> R.id.meter_rb
                Constants.MILES_HOUR_OPTION -> R.id.miles_rb
                else -> 0
            }
        )
        binding.tempGroup.check(
            when (SettingsSetup.getTempUnits()) {
                Constants.TEMP_CELSIUS_OPTION -> R.id.celsius_rb
                Constants.TEMP_FAHRENHEIT_OPTION -> R.id.fahrenheit_rb
                else -> R.id.kelvin_rb
            }
        )
        binding.langGroup.check(
            when (SettingsSetup.getLanguage()) {
                Constants.LANG_ENG_OPTION -> R.id.english_rb
                else -> R.id.arabic_rb
            }
        )
        binding.locationGroup.check(
            when (SettingsSetup.getLocation()) {
                Constants.GPS_OPTION -> R.id.gps_rb
                else -> R.id.map_rb
            }
        )
    }

    private fun changeSettings() {
        binding.tempGroup.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.celsius_rb -> settingsViewModel.setMetric()
                R.id.fahrenheit_rb -> settingsViewModel.setImperial()
                R.id.kelvin_rb -> settingsViewModel.setStandard()
            }
        }

        binding.windSpeedGroup.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.meter_rb -> settingsViewModel.setMeter()
                R.id.miles_rb -> settingsViewModel.setMile()
            }
        }

        binding.mapRb.setOnClickListener {
            settingsViewModel.setMap()
            Navigation.findNavController(binding.root)
                .navigate(SettingsFragmentDirections.actionNavSettingsToMapsFragment())
        }

        binding.gpsRb.setOnClickListener {
            settingsViewModel.SetGPS(requireContext())
        }

        binding.langGroup.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.arabic_rb -> settingsViewModel.setArabic(requireContext())
                R.id.english_rb -> settingsViewModel.setEnglish(requireContext())
            }
        }
    }

    override fun onPause() {
        super.onPause()
        settingsViewModel.saveChanges()
    }
}
