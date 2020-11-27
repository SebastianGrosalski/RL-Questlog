package com.example.questtask.ui.hello

import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
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
        val navBar: BottomNavigationView =
            (activity as AppCompatActivity).findViewById(R.id.nav_view)
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

        binding.etName.imeOptions = EditorInfo.IME_ACTION_DONE

        viewModel.navigateToInitialPreferences.observe(viewLifecycleOwner, Observer {
            if (containsName()) {
                if (findNavController().currentDestination?.id == R.id.helloFragment && it) {
                    var name: String = binding.etName.text.toString()
                    viewModel.setText(name)

                    viewModel.doneNavigating()
                }
            } else {
                Toast.makeText(
                    this.context,
                    resources.getString(R.string.toastMsgName),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        return binding.root
    }

    private fun containsName() = (
            binding.etName.text.toString().trim().isNotEmpty()
                    || binding.etName.text.toString().trim().isNotBlank())
}

