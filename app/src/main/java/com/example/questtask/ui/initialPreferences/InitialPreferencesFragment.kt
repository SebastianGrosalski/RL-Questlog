package com.example.questtask.ui.initialPreferences

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.questtask.R
import com.example.questtask.databinding.FragmentInitialPreferencesBinding
import com.example.questtask.util.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_initial_preferences.*


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
            if(isNothingChecked())
            {
                Toast.makeText(this.context,
                    resources.getString(R.string.toastMsgCb),
                    Toast.LENGTH_SHORT).show()
            } else {
                mapToPref()
                if (findNavController().currentDestination?.id == R.id.initialPreferences && it) {
                    findNavController().navigate(
                        InitialPreferencesFragmentDirections
                            .actionInitialPreferencesToNavigationHome()
                    )
                    viewModel.doneNavigating()
                }
            }
        })
        return binding.root
    }

    private fun mapToPref() {
        val map = HashMap<String, Boolean>()
        map[TIDINESS] = binding.cbTidiness.isChecked
        map[WORK] = binding.cbWork.isChecked
        map[HEALTH] = binding.cbHealth.isChecked
        map[KNOWLEDGE] = binding.cbKnowledge.isChecked
        map[FITNESS] = binding.cbFitness.isChecked
        map[DIET] = binding.cbDiet.isChecked
        viewModel.putTopics(map)
    }

    // Make App-bar and bottom navigation visible when navigating to home-fragment
    override fun onDestroy() {
        super.onDestroy()
        (activity as AppCompatActivity).supportActionBar?.show()
        val navBar: BottomNavigationView = (activity as AppCompatActivity).findViewById(R.id.nav_view)
        navBar.isVisible = true
    }
    // Check whether any of the checkboxes are checked
    private fun isNothingChecked() = (
            !binding.cbDiet.isChecked
            && !binding.cbWork.isChecked
            && !binding.cbHealth.isChecked
            && !binding.cbKnowledge.isChecked
            && !binding.cbFitness.isChecked
            && !binding.cbTidiness.isChecked)
}
