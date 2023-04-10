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
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherly.R
import com.example.weatherly.databinding.FragmentMapsBinding
import com.example.weatherly.db.ConcreteLocalSource
import com.example.weatherly.model.Repository
import com.example.weatherly.network.RetrofitClient
import com.example.weatherly.utils.Constants
import com.example.weatherly.utils.SettingsSetup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapsFragment : Fragment() {
    private lateinit var mMap: GoogleMap
    private var mapLat: Double = 31.104994885376325
    private var mapLon: Double = 29.775266209000975
    private lateinit var binding: FragmentMapsBinding
    lateinit var mapViewModelFactory: MapViewModelFactory
    lateinit var mapViewModel: MapViewModel

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->

        mMap = googleMap
        val home = LatLng(31.104994885376325, 29.775266209000975)
        mMap.addMarker(MarkerOptions().position(home).title("Default Marker"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(home))
        setMapOnClickLocation(mMap)
        setMapOnLongClickLocation(mMap)
        mMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                requireContext(),
                R.raw.map_style_dark
            )
        )
        mMap.isMyLocationEnabled = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        mapViewModelFactory = MapViewModelFactory(
            Repository.getInstance(
                RetrofitClient.getInstance(),
                ConcreteLocalSource.getInstance(requireContext())
            )
        )
        mapViewModel =
            ViewModelProvider(this, mapViewModelFactory).get(MapViewModel::class.java)
        return (binding.root)
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
                val address: Address = addressList?.get(0) ?: Address(Locale.getDefault())
                val latLng = LatLng(address.latitude, address.longitude)
                mMap.addMarker(MarkerOptions().position(latLng).title(location))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))

                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
        val locationButton =
            (mapView!!.findViewById<View>(Integer.parseInt("1")).parent as View).findViewById<View>(
                Integer.parseInt("2")
            )
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
            Log.i("lat in setMap", mapLat.toString())
            Log.i("lon in setMap", mapLon.toString())
            Log.i("latLng", latLng.latitude.toString())
        }
    }

    private fun setMapOnLongClickLocation(map: GoogleMap) {
        map.setOnMapLongClickListener { mlatLng->
            try {
                mapViewModel.storeLocation(mlatLng.latitude,mlatLng.longitude)
//                map.addMarker(MarkerOptions()
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
                Toast.makeText(requireContext(),"Saved Location",Toast.LENGTH_SHORT).show()
            }catch (e : Exception){
                e.printStackTrace()
                Toast.makeText(requireContext(),"please try again",Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun initMap() {
        Toast.makeText(requireContext(),"Click To Change your Location Or Long Click To save Location",Toast.LENGTH_LONG).show()
        val shared = SettingsSetup.getSharedPref()
        shared.getString(Constants.LAT_VALUE, "Empty")
            ?.let {
                SettingsSetup.setLatitude(it.toDouble())
                println(SettingsSetup.getLatitude().toString() + "init Map")
            }
        shared.getString(Constants.LON_VALUE, "Empty")
            ?.let {
                SettingsSetup.setLongitude(it.toDouble())
                println(SettingsSetup.getLongitude().toString() + "init Map")
            }
        Log.i("Init map", " initialized")
    }
}