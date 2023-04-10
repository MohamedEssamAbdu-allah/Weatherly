package com.example.weatherly.ui.week

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherly.databinding.DailyCardviewBinding
import com.example.weatherly.model.Daily
import com.example.weatherly.model.WeatherDetails
import com.example.weatherly.utils.Constants

class WeekAdapter (
    private val context: Context,
    private val locations: List<Daily>,
    private val clickListener: DayClickListener
) :
    RecyclerView.Adapter<WeekAdapter.MyViewHolder>() {

    class MyViewHolder(var binding: DailyCardviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = DailyCardviewBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val day = locations[position]
        val iconUrl = "https://openweathermap.org/img/wn/${day.weather.get(0).icon}.png"
        holder.binding.dayTime = WeatherDetails.getDate(locations[position].dt.toLong())
        holder.binding.dayObj = locations[position]
        holder.binding.dayAction = clickListener
        holder.binding.bindingDailySymbol = Constants.KELVIN
        Glide.with(context).load(iconUrl).into(holder.binding.dayIcon)

    }

    override fun getItemCount(): Int {
        return locations.size
    }
}