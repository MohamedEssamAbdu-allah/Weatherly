package com.example.weatherly.ui.map

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.weatherly.R
import com.example.weatherly.databinding.FragmentMapsBinding
import com.example.weatherly.utils.Constants
import com.example.weatherly.utils.SettingsSetup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {
    private lateinit var mMap: GoogleMap
    private var mapLat: Double = 31.104994885376325
    private var mapLon: Double = 29.775266209000975
    private lateinit var binding: FragmentMapsBinding
    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->

        mMap = googleMap
        val home = LatLng(31.104994885376325, 29.775266209000975)
        mMap.addMarker(MarkerOptions().position(home).title("Default Marker"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(home))
        setMapOnClickLocation(mMap)
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style_dark))
        mMap.isMyLocationEnabled = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(inflater, container, false)

        return(binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        val mapView = mapFragment?.view
        mapFragment?.getMapAsync(callback)
        initMap()

        binding.idSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val location: String = binding.idSearchView.query.toString()
                var addressList: List<Address>? = null
                val geocoder = Geocoder(requireContext())
                try {
                    addressList = geocoder.getFromLocationName(location, 1)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                val address: Address = addressList!![0]
                val latLng = LatLng(address.latitude, address.longitude)
                mMap.addMarker(MarkerOptions().position(latLng).title(location))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))

                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
        val locationButton = (mapView!!.findViewById<View>(Integer.parseInt("1")).parent as View).findViewById<View>(Integer.parseInt("2"))
        val layoutParams = locationButton.layoutParams as RelativeLayout.LayoutParams
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        layoutParams.setMargins(0, 0, 30, 30)
        locationButton.layoutParams = layoutParams

    }

    private fun setMapOnClickLocation(map: GoogleMap) {
        map.setOnMapClickListener { latLng ->
            map.addMarker(MarkerOptions().position(latLng))
            mapLat = latLng.latitude
            mapLon = latLng.longitude
            SettingsSetup.setLatitude(mapLat)
            SettingsSetup.setLongitude(mapLon)
            Log.i("lat in setMap",mapLat.toString())
            Log.i("lon in setMap",mapLon.toString())
            Log.i("latLng",latLng.latitude.toString())
        }
    }

    private fun initMap(){
        val shared =SettingsSetup.getSharedPref()
        shared.getString(Constants.LAT_VALUE, "Empty")
            ?.let { SettingsSetup.setLatitude(it.toDouble())
                println(SettingsSetup.getLatitude().toString() + "init Map")
            }
        shared.getString(Constants.LON_VALUE, "Empty")
            ?.let { SettingsSetup.setLongitude(it.toDouble())
                println(SettingsSetup.getLongitude().toString() + "init Map")
            }
        Log.i("Init map", " initialized")
    }
}