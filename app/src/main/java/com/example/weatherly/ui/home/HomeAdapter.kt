package com.example.weatherly.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherly.databinding.HourlyCardviewBinding
import com.example.weatherly.model.Hourly
import java.text.SimpleDateFormat
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
        val date = Date(hours[position].dt.toLong() * 1000) // Create a new Date object with the Unix timestamp in milliseconds
        val dateFormat = SimpleDateFormat("HH", Locale.getDefault()) // Define the date format
        val formattedDate = dateFormat.format(date)
        val hour = hours[position]
        val iconUrl = "https://openweathermap.org/img/wn/${hour.weather.get(0).icon}.png"
//        Glide.with(requireContext()).load(iconUrl).into(binding.weatherIcon)
        holder.binding.hourTv.text = formattedDate
        holder.binding.hourTempTv.text = hours[position].temp.toString()
        Glide.with(context).load(iconUrl).into(holder.binding.hourIcon)
    }
}
