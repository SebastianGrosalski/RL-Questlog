package com.example.questtask.ui.questdetail

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.questtask.R
import com.example.questtask.databinding.FragmentQuestDetailBinding
import com.example.questtask.repository.classes.Friend
import com.example.questtask.repository.firebase.FirebaseRepository
import com.example.questtask.repository.room.Quest
import com.example.questtask.util.*
import kotlinx.coroutines.launch

class QuestDetailFragment : Fragment() {

    private lateinit var binding : FragmentQuestDetailBinding
    private lateinit var viewModel: QuestDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = arguments?.let { QuestDetailFragmentArgs.fromBundle(it) }
        val questId = args?.questId!!
        val application = requireNotNull(this.activity).application
        val viewModelFactory = QuestDetailViewModelFactory(application, questId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(QuestDetailViewModel::class.java)
        val quest = viewModel.quest

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_quest_detail,
            container,
            false
        )

        binding.tvFromUser.text = quest.from

        binding.detailTitle.text = quest.title
        when (quest.difficulty){
            1 -> {
                binding.detailTitle.setTextColor(Color.parseColor(VERY_EASY_COLOR))
            }
            2 -> {
                binding.detailTitle.setTextColor(Color.parseColor(EASY_COLOR))
            }
            3 -> {
                binding.detailTitle.setTextColor(Color.parseColor(MEDIUM_COLOR))
            }
            4 -> {
                binding.detailTitle.setTextColor(Color.parseColor(HARD_COLOR))
            }
            5 -> {
                binding.detailTitle.setTextColor(Color.parseColor(VERY_HARD_COLOR))
            }
            else -> binding.detailTitle.setTextColor(Color.WHITE)
        }

        binding.detailShortDescription.text = quest.description_short
        binding.detailLongDescription.text = quest.description_long

        when(quest.topic){
            DIET -> binding.imgTopic.setImageResource(R.drawable.ic_diet)
            KNOWLEDGE -> binding.imgTopic.setImageResource(R.drawable.ic_knowledge)
            FITNESS -> binding.imgTopic.setImageResource(R.drawable.ic_fitness)
            WORK -> binding.imgTopic.setImageResource(R.drawable.ic_work)
            HEALTH -> binding.imgTopic.setImageResource(R.drawable.ic_health)
            else -> binding.imgTopic.setImageResource(R.drawable.ic_tidiness)
        }

        fun getPointAmount(quest : Quest) : Int{
            return quest.difficulty!!.times(10)
        }

        fun buildShareDialog(){
            val builder = AlertDialog.Builder(activity)
            val dialog = builder.setTitle("Teilen mit:")

            val view = layoutInflater.inflate(R.layout.dialog_share, null)
            builder.setView(view)
            val spinner = view.findViewById<Spinner>(R.id.dialog_spinner)

            viewModel.uiScope.launch {
                val friendList = FirebaseRepository.getAllFriends()
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, friendList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }

            var friend : Friend? = null
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long){
                    friend = parent?.getItemAtPosition(position) as Friend
                }
            }
            dialog.setPositiveButton("Teilen"){
                _, _ ->  FirebaseRepository.shareQuest(quest, friend!!)
            }

                .setNegativeButton("Abbrechen"){
                    _,_ ->
                }.create()
            dialog.show()
        }

        binding.btnShare.setOnClickListener{
            buildShareDialog()
        }

        //Visibility Settings for Detailbutton//
        binding.tvPoints.text = getPointAmount(quest).toString()
        if(quest.accepted == false){
            binding.btnAccept.visibility = View.VISIBLE
            binding.btnSubmit.visibility = View.GONE
            binding.btnRepeat.visibility = View.GONE
        } else if (quest.done == false)
        {
            binding.btnAccept.visibility = View.GONE
            binding.btnRepeat.visibility = View.GONE
            binding.btnSubmit.visibility = View.VISIBLE
        } else {
            binding.btnAccept.visibility = View.GONE
            binding.btnRepeat.visibility = View.VISIBLE
            binding.btnSubmit.visibility = View.GONE
        }

        binding.btnAccept.setOnClickListener {
            viewModel.uiScope.launch {
                viewModel.ioScope.launch {
                    viewModel.updateAccepted(quest.id)
                    quest.accepted = true
                    quest.done = false
                    FirebaseRepository.updateQuest(quest)
                }
            }
            findNavController().navigate(QuestDetailFragmentDirections.actionQuestDetailFragmentToQuestFragment())
        }

        binding.btnSubmit.setOnClickListener {
            viewModel.uiScope.launch {
                viewModel.ioScope.launch {
                    Log.i("QUESTDETAILFRAGMENT", "QUEST ABGEGEBEN: ${quest.difficulty?.times(10)!!}")
                    viewModel.questDone(quest.id, quest.topic!!, quest!!.difficulty!!.times(10))
                    quest.done = true
                    quest.accepted = false
                    FirebaseRepository.updateQuest(quest)
                }
            }
            findNavController().navigate(QuestDetailFragmentDirections.actionQuestDetailFragmentToQuestFragment())
        }

        binding.btnRepeat.setOnClickListener {
            viewModel.uiScope.launch {
                viewModel.ioScope.launch {
                    viewModel.resetQuest(quest.id)
                    quest.done = false
                    quest.accepted = false
                    FirebaseRepository.updateQuest(quest)
                }
            }
            findNavController().navigate(QuestDetailFragmentDirections.actionQuestDetailFragmentToQuestFragment())
        }
        return binding.root
    }
}
