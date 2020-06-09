package com.example.questtask.ui.initialPreferences

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider

import com.example.questtask.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class InitialPreferencesFragment : Fragment() {

    companion object {
        fun newInstance() = InitialPreferencesFragment()
    }

    private lateinit var viewModel: InitialPreferencesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Hide the App bar
        (activity as AppCompatActivity).supportActionBar?.hide()
        // Hide the BottomNavigation for this Fragment
        val navBar: BottomNavigationView = (activity as AppCompatActivity).findViewById(R.id.nav_view)
        navBar.isVisible = false
        return inflater.inflate(R.layout.fragment_initial_preferences, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(InitialPreferencesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
