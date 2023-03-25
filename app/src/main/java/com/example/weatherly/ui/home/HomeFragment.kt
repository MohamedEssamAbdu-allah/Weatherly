package com.example.weatherly.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.weatherly.databinding.FragmentHomeBinding


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
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}