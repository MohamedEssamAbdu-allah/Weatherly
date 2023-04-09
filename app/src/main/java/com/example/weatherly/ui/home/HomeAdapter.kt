package com.example.weatherly.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherly.databinding.HourlyCardviewBinding
import com.example.weatherly.model.Hourly
import com.example.weatherly.utils.Constants
import java.text.DateFormat
import java.util.*

class HomeAdapter(
    private val context: Context,
    private val hours: List<Hourly>,
    private val clickListener: HomeClickListener
) :
    RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {


    class MyViewHolder(var binding: HourlyCardviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = HourlyCardviewBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return hours.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val date = Date(hours[position].dt.toLong() * 1000)
        val dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT)
        val formattedDate = dateFormat.format(date)
        val hour = hours[position]
        val iconUrl = "https://openweathermap.org/img/wn/${hour.weather.get(0).icon}.png"
        holder.binding.time = formattedDate
        holder.binding.hourlyObj = hours[position]
        holder.binding.bindingHourlySymbol = Constants.KELVIN
        holder.binding.action = clickListener
        Glide.with(context).load(iconUrl).into(holder.binding.hourIcon)

    }
}