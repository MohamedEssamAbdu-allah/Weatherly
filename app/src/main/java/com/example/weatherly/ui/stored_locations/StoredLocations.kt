package com.example.weatherly.ui.stored_locations

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherly.R

class StoredLocations : Fragment() {

    companion object {
        fun newInstance() = StoredLocations()
    }

    private lateinit var viewModel: StoredLocationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stored_locations, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StoredLocationsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}