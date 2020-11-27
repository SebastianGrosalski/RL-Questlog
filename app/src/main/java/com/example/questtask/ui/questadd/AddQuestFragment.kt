package com.example.questtask.ui.questadd

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.questtask.R

import com.example.questtask.databinding.FragmentAddQuestBinding
import com.example.questtask.repository.QuestRepository
import com.example.questtask.repository.firebase.FirebaseAuthenticate
import com.example.questtask.repository.firebase.FirebaseRepository
import com.example.questtask.repository.room.Quest
import com.example.questtask.repository.room.QuestDatabase
import com.example.questtask.util.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_add_quest.*
import kotlinx.coroutines.launch

class AddQuestFragment : Fragment() {
    private lateinit var viewModel: AddQuestViewModel
    private lateinit var binding: FragmentAddQuestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_quest,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(AddQuestViewModel::class.java)
        val mAuth = Firebase.auth
        //Spinner for topic
        ArrayAdapter.createFromResource(
            this?.requireContext(),
            R.array.topics_array,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerTopic.adapter = arrayAdapter
        }

        //Spinner for difficulty
        ArrayAdapter.createFromResource(
            this?.requireContext(),
            R.array.difficulty_array,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerDifficulty.adapter = arrayAdapter
        }

        binding.spinnerTopic.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (binding.spinnerTopic.selectedItem.toString()) {
                    "Ordnung" -> viewModel.questTopic = TIDINESS
                    "Fitness" -> viewModel.questTopic = FITNESS
                    "Arbeit" -> viewModel.questTopic = WORK
                    "Gesundheit" -> viewModel.questTopic = HEALTH
                    "Ernährung" -> viewModel.questTopic = DIET
                    else -> viewModel.questTopic = KNOWLEDGE
                }
            }
        }

        binding.spinnerDifficulty.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (binding.spinnerDifficulty.selectedItem.toString()) {
                        "Sehr Leicht" -> {
                            viewModel.questDifficulty = VERY_EASY
                            binding.tvPointValue.setTextColor(Color.parseColor(VERY_EASY_COLOR))
                            binding.tvPointValue.text = (VERY_EASY * 10).toString()
                        }
                        "Leicht" -> {
                            viewModel.questDifficulty = EASY
                            binding.tvPointValue.setTextColor(Color.parseColor(EASY_COLOR))
                            binding.tvPointValue.text = (EASY * 10).toString()
                        }
                        "Mittel" -> {
                            viewModel.questDifficulty = MODERATE
                            binding.tvPointValue.setTextColor(Color.parseColor(MEDIUM_COLOR))
                            binding.tvPointValue.text = (MODERATE * 10).toString()
                        }
                        "Schwierig" -> {
                            viewModel.questDifficulty = HARD
                            binding.tvPointValue.setTextColor(Color.parseColor(HARD_COLOR))
                            binding.tvPointValue.text = (HARD * 10).toString()
                        }
                        else -> {
                            viewModel.questDifficulty = VERY_HARD
                            binding.tvPointValue.setTextColor(Color.parseColor(VERY_HARD_COLOR))
                            binding.tvPointValue.text = (VERY_HARD * 10).toString()
                        }
                    }
                }
            }

        fun containsTitle(): Boolean =
            binding.teTitle.text.toString().trim().isNotEmpty() ||
                    binding.teTitle.text.toString().trim().isNotBlank()

        fun containsShortDescription(): Boolean =
            binding.teShortDesc.text.toString().trim().isNotEmpty() ||
                    binding.teShortDesc.text.toString().trim().isNotBlank()

        fun containsLongDescription(): Boolean =
            binding.teLongDesc.text.toString().trim().isNotEmpty() ||
                    binding.teLongDesc.text.toString().trim().isNotBlank()


        binding.fabComplete.setOnClickListener(View.OnClickListener {
            if (containsTitle()) {
                viewModel.questTitle = binding.teTitle.text.toString()
            } else {
                Toast.makeText(
                    this.context,
                    "Du musst einen Titel eingeben!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            if (containsShortDescription()) {
                viewModel.questShortDesc = binding.teShortDesc.text.toString()
            } else {
                Toast.makeText(
                    this.context,
                    "Du musst eine kurze Beschreibung eingeben!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            //Write contents to db && navigate if required fields are filled
            if (containsLongDescription()) {
                viewModel.questLongDesc = binding.teLongDesc.text.toString()
            } else {
                Toast.makeText(
                    this.context,
                    "Du musst eine längere Beschreibung eingeben!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (containsTitle() && containsShortDescription() && containsLongDescription()) {
                viewModel.uiScope.launch {
                    viewModel.ioScope.launch {
                        viewModel.addQuest(
                            Quest(
                                0,
                                title = viewModel.questTitle,
                                description_short = viewModel.questShortDesc,
                                description_long = viewModel.questLongDesc,
                                topic = viewModel.questTopic,
                                from = FirebaseRepository.getNameById(mAuth.currentUser!!.uid),
                                difficulty = viewModel.questDifficulty,
                                firestoreId = (mAuth.currentUser!!.uid.toString() + id)
                            )
                        )
                    }
                }
                this.findNavController().navigate(AddQuestFragmentDirections.actionAddQuestFragmentToQuestFragment())
            }
        }

        )

        return binding.root

    }}
