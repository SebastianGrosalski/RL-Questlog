package com.example.questtask.ui.settings

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.questtask.R
import com.example.questtask.databinding.FragmentSettingsBinding
import com.example.questtask.util.*
import com.google.android.material.bottomnavigation.BottomNavigationView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding : FragmentSettingsBinding
    private lateinit var viewModel : SettingsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val navBar: BottomNavigationView =
            (activity as AppCompatActivity).findViewById(R.id.nav_view)
        navBar.isVisible = false
        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_settings,
            container,
            false
        )
        setInitialStates()
        binding.btnSettings.setOnClickListener{
            saveData()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment settingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun setInitialStates(){
        binding.etName.setText(viewModel.prefProvider.getName())
        binding.cbTidiness.isChecked = viewModel.prefProvider.getTidiness()!!
        binding.cbWork.isChecked = viewModel.prefProvider.getWork()!!
        binding.cbHealth.isChecked = viewModel.prefProvider.getHealth()!!
        binding.cbFitness.isChecked = viewModel.prefProvider.getFitness()!!
        binding.cbDiet.isChecked = viewModel.prefProvider.getDiet()!!
        binding.cbKnowledge.isChecked = viewModel.prefProvider.getKnowledge()!!
    }

    private fun isNothingChecked() = (
            !binding.cbDiet.isChecked
                    && !binding.cbWork.isChecked
                    && !binding.cbHealth.isChecked
                    && !binding.cbKnowledge.isChecked
                    && !binding.cbFitness.isChecked
                    && !binding.cbDiet.isChecked)

    private fun containsName() = (
            binding.etName.text.toString().trim().isNotEmpty()
                    || binding.etName.text.toString().trim().isNotBlank())


    private fun mapToPref() {
        val map = HashMap<String, Boolean>()
        map[TIDINESS] = binding.cbTidiness.isChecked
        map[WORK] = binding.cbWork.isChecked
        map[HEALTH] = binding.cbHealth.isChecked
        map[KNOWLEDGE] = binding.cbKnowledge.isChecked
        map[FITNESS] = binding.cbFitness.isChecked
        map[DIET] = binding.cbDiet.isChecked
        viewModel.prefProvider.putPreferredTopics(map)
    }

    private fun saveData(){
        if(isNothingChecked()){
            Toast.makeText(this.context,
                "Du musst eine Kategorie auswählen!",
                Toast.LENGTH_SHORT).show()
        }
        else if(!containsName()){
            Toast.makeText(this.context,
            "Du musst einen Namen eingeben!",
            Toast.LENGTH_SHORT).show()
        }
        else {
            mapToPref()
            viewModel.prefProvider.putName(binding.etName.text.toString())
            Toast.makeText(this.context,
            "Änderungen gespeichert!",
            Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }
}

