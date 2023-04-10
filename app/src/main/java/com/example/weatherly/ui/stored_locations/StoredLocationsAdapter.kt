package com.example.weatherly.ui.stored_locations

import android.content.Context
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherly.databinding.StoredCardviewBinding
import com.example.weatherly.model.WeatherDetails
import com.example.weatherly.model.WeatherModel
import com.example.weatherly.ui.week.DayClickListener
import com.example.weatherly.utils.Constants
import com.example.weatherly.utils.SettingsSetup
import java.util.*

class StoredLocationsAdapter (
    private val context: Context,
    private val locations: List<WeatherModel>,
    private val clickListener: LocationClickListener
) :
    RecyclerView.Adapter<StoredLocationsAdapter.MyViewHolder>() {

    class MyViewHolder(var binding: StoredCardviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = StoredCardviewBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    val geoCoder = Geocoder(context, Locale.getDefault())

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val location = locations[position]
        val iconUrl = "https://openweathermap.org/img/wn/${location.current.weather.get(0).icon}.png"
        holder.binding.locationData = locations[position]
        holder.binding.action = clickListener
        holder.binding.locationName = geoCoder.getFromLocation(location.lat,location.lon,1)?.get(0)?.adminArea ?: "Unkown"
        holder.binding.locationSymbol = Constants.KELVIN
        Glide.with(context).load(iconUrl).into(holder.binding.locationIcon)

    }

    override fun getItemCount(): Int {
        return locations.size
    }
}