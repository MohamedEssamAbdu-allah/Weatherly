package com.example.weatherly.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.weatherly.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        /*
        NOTE UNICODES FOR WEATHER PREFERENCES
        Celsius = \u2103
        Fahrenheit = \u2109
         */

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.weekTv.setOnClickListener {
            Navigation.findNavController(root)
                .navigate(HomeFragmentDirections.actionNavHomeToWeekFragment())
        }


        homeViewModel.time.observe(viewLifecycleOwner) {
            binding.timeTv.text = it
        }
        homeViewModel.date.observe(viewLifecycleOwner){
            binding.dateTv.text = it
        }

        //val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            binding.timeTv.text = it
//        }

        //unixTimeStamp Conversion
//        val unixTimestamp = 1679717068L // Unix timestamp in seconds
//        val date = Date(unixTimestamp * 1000) // Create a new Date object with the Unix timestamp in milliseconds
//        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) // Define the date format
//        val formattedDate = dateFormat.format(date)
//        println(formattedDate)




        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}