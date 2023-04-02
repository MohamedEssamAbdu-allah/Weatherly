package com.example.weatherly.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class MyDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Please Turn on your GPS")
                .setPositiveButton("OK") { _, i -> enableLocationSettings() }
                .setNegativeButton("NO") { _, i -> Toast.makeText(requireContext(),"UnAvailable",Toast.LENGTH_SHORT).show() }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun enableLocationSettings() {
        val settingsIntent: Intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(settingsIntent)

    }
}