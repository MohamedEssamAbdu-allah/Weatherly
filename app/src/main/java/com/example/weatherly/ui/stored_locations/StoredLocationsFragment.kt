package com.example.weatherly.ui.stored_locations

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.weatherly.databinding.FragmentStoredLocationsBinding
import com.example.weatherly.db.ConcreteLocalSource
import com.example.weatherly.model.Repository
import com.example.weatherly.model.WeatherModel
import com.example.weatherly.network.RetrofitClient

class StoredLocationsFragment : Fragment(), LocationClickListener {

    private lateinit var viewModel: StoredLocationsViewModel
    private lateinit var _binding: FragmentStoredLocationsBinding
    private lateinit var viewModelFactory: StoredLocationsFactory
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStoredLocationsBinding.inflate(inflater, container, false)
        _binding.addLocationFAB.setOnClickListener {
            Navigation.findNavController(_binding.root)
                .navigate(StoredLocationsFragmentDirections.actionStoredLocationsToMapsFragment())
        }
        viewModelFactory = StoredLocationsFactory(
            Repository.getInstance(
                RetrofitClient.getInstance(),
                ConcreteLocalSource.getInstance(requireContext())
            )
        )
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(StoredLocationsViewModel::class.java)
        viewModel.storedLocations.observe(viewLifecycleOwner) {
            val locAdapter = StoredLocationsAdapter(requireContext(), it, this)
            _binding.locationsAdapter = locAdapter
        }
        return _binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun showStoredLocation(weatherModel: WeatherModel) {
        Navigation.findNavController(_binding.root)
            .navigate(StoredLocationsFragmentDirections.actionNavLocationsToLocationDetailsFragment(weatherModel))
    }

    override fun deleteStoredLocation(weatherModel: WeatherModel) {
        viewModel.deleteStoredLocation(weatherModel)
    }

}