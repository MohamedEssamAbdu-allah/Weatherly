package com.example.weatherly.ui.splash_screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.weatherly.ui.main_activity.MainActivity
import com.example.weatherly.databinding.ActivitySplashScreenBinding
import com.example.weatherly.utils.Constants
import com.example.weatherly.utils.MyDialog
import com.example.weatherly.utils.SettingsSetup
import com.google.android.gms.location.*
import java.util.concurrent.TimeUnit

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var fusedLocationProvider: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val splashScreenViewModel = ViewModelProvider(this).get(SplashScreenViewModel::class.java)
        splashScreenViewModel.initSettings(this)
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)
        SettingsSetup.setLang(SettingsSetup.getLanguage(),this)
        Handler(Looper.getMainLooper())
    }

    override fun onResume() {
        super.onResume()
        if (checkPermissions()) {
            if (SettingsSetup.getSharedPref().getString(Constants.LOCATION_KEY,"Empty").equals(Constants.GPS_OPTION)){
                getLastLocation()
            } else {
                initLocationUsingMap(SettingsSetup.getSharedPref())
            }
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
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
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
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                requestNewLocationData()
            } else {
                val myDialog = MyDialog()
                myDialog.show(supportFragmentManager, "GPS IS OFF")
            }
        } else {
            requestPermissions()
        }

    }

    @SuppressLint("MissingPermission")
    fun requestNewLocationData() {
        val myLocationRequest = LocationRequest.create().apply {
            interval = TimeUnit.HOURS.toMillis(1)
            maxWaitTime = 3000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProvider.requestLocationUpdates(
            myLocationRequest, myLocationCallback, Looper.myLooper()
        )
    }

    private val myLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val myLastLocation = locationResult.lastLocation
            Log.i("latitutde", myLastLocation?.latitude.toString())
            Log.i("Longititude", myLastLocation?.longitude.toString())
            val intent = Intent(this@SplashScreen, MainActivity::class.java)
//            Location.lat = myLastLocation?.latitude!!
//            Location.lon = myLastLocation.longitude
            SettingsSetup.setLatitude(myLastLocation!!.latitude)
            SettingsSetup.setLongitude(myLastLocation.longitude)
            Log.i("SettingsSetup Lat ", SettingsSetup.getLatitude().toString())
            Log.i("SettingsSetup Lon ", SettingsSetup.getLongitude().toString())
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

    private fun initLocationUsingMap(sharedPreferences: SharedPreferences) {
        SettingsSetup.setLatitude(
            sharedPreferences.getString(Constants.LAT_VALUE, "0.0")!!.toDouble()
        )
        SettingsSetup.setLongitude(
            sharedPreferences.getString(Constants.LON_VALUE, "0.0")!!.toDouble()
        )
        val intent = Intent(this@SplashScreen, MainActivity::class.java)
        startActivity(intent)
    }

}

