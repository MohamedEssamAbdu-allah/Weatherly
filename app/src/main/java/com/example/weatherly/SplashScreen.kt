package com.example.weatherly

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.weatherly.databinding.ActivitySplashScreenBinding
import com.example.weatherly.utils.Constants
import com.example.weatherly.utils.Location
import com.example.weatherly.utils.MyDialog
import com.google.android.gms.location.*

class SplashScreen : AppCompatActivity() {

    //private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var fusedLocationProvider: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
        Handler(Looper.getMainLooper())
    }

    override fun onResume() {
        super.onResume()
        if (checkPermissions()) {
            getLastLocation()
        }
    }

    fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ), Constants.MyPermission
        )
    }

    fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    fun getLastLocation() {
        if (checkPermissions()){
            if (isLocationEnabled()){
                requestNewLocationData()
            } else {
                val myDialog = MyDialog()
                myDialog.show(supportFragmentManager,"GPS IS OFF")
            }
        } else{
            requestPermissions()
        }

    }

    @SuppressLint("MissingPermission")
    fun requestNewLocationData(){
       val myLocationRequest = LocationRequest.create().apply {
            interval = 1000
            maxWaitTime = 3000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProvider.requestLocationUpdates(myLocationRequest,myLocationCallback, Looper.myLooper())
    }
    private val myLocationCallback : LocationCallback = object :LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val myLastLocation = locationResult.lastLocation
            Log.i("latitutde",myLastLocation?.latitude.toString())
            Log.i("Longititude",myLastLocation?.longitude.toString())
            val intent = Intent(this@SplashScreen, MainActivity::class.java)
            Location.lat = myLastLocation?.latitude!!
            Location.lon = myLastLocation.longitude
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.MyPermission) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            }
        }
    }


}

/*
// Create a LocationManager object
val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

// Check if location services are enabled
if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

    // Request location updates
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            // Handle location change
            val latitude = location?.latitude
            val longitude = location?.longitude
            Log.d("Location", "Latitude: $latitude, Longitude: $longitude")

            // Stop listening for location updates after first update
            locationManager.removeUpdates(this)
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        override fun onProviderEnabled(provider: String?) {}
        override fun onProviderDisabled(provider: String?) {}
    })
}
 */

