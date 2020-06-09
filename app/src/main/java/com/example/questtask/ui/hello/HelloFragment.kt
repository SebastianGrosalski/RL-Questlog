package com.example.questtask.ui.hello

import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.questtask.R
import com.example.questtask.databinding.FragmentHelloBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log

/**
 * A simple [Fragment] subclass.
 */
class HelloFragment : Fragment() {
    private lateinit var binding: FragmentHelloBinding
    private lateinit var viewModel: HelloViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Hide the App bar
        (activity as AppCompatActivity).supportActionBar?.hide()
        // Hide the BottomNavigation for this Fragment
        val navBar: BottomNavigationView = (activity as AppCompatActivity).findViewById(R.id.nav_view)
        navBar.isVisible = false
        // Get the ViewModel
        viewModel = ViewModelProvider(this).get(HelloViewModel::class.java)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_hello,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.navigateToInitialPreferences.observe(viewLifecycleOwner, Observer {
            if(findNavController().currentDestination?.id == R.id.helloFragment && it){
                    this.findNavController().navigate(
                    HelloFragmentDirections.actionHelloFragmentToInitialPreferences())
                    viewModel.doneNavigating()
            }
        })
        return binding.root
    }
}
