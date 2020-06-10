package com.example.questtask.ui.initialPreferences

import android.os.Bundle
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
import com.example.questtask.databinding.FragmentInitialPreferencesBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class InitialPreferencesFragment : Fragment() {

    companion object {
        fun newInstance() = InitialPreferencesFragment()
    }
    private lateinit var viewModel: InitialPreferencesViewModel
    private lateinit var binding: FragmentInitialPreferencesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Hide the App bar
        (activity as AppCompatActivity).supportActionBar?.hide()
        // Hide the BottomNavigation for this Fragment
        val navBar: BottomNavigationView = (activity as AppCompatActivity).findViewById(R.id.nav_view)
        navBar.isVisible = false
        // Get the ViewModel
        viewModel = ViewModelProvider(this).get(InitialPreferencesViewModel::class.java)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_initial_preferences,
            container,
            false
        )
        binding.viewModel = viewModel

        viewModel.navigate.observe(viewLifecycleOwner, Observer{
            if(findNavController().currentDestination?.id == R.id.initialPreferences && it){
                findNavController().navigate(
                    InitialPreferencesFragmentDirections
                        .actionInitialPreferencesToNavigationHome())

            }
        })
        return binding.root
    }

    // Make App-bar and bottom navigation visible when navigating to home-fragment
    override fun onDestroy() {
        super.onDestroy()
        (activity as AppCompatActivity).supportActionBar?.show()
        val navBar: BottomNavigationView = (activity as AppCompatActivity).findViewById(R.id.nav_view)
        navBar.isVisible = true
    }
}
