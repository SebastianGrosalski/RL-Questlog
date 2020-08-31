package com.example.questtask.ui.questdetail

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.questtask.R
import com.example.questtask.databinding.FragmentQuestDetailBinding
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
                }
            }
            findNavController().navigate(QuestDetailFragmentDirections.actionQuestDetailFragmentToQuestFragment())
        }

        binding.btnSubmit.setOnClickListener {
            viewModel.uiScope.launch {
                viewModel.ioScope.launch {
                    Log.i("QUESTDETAILFRAGMENT", "QUEST ABGEGEBEN: ${quest.difficulty?.times(10)!!}")
                    viewModel.questDone(quest.id, quest.topic!!, quest.difficulty.times(10))
                }
            }
            findNavController().navigate(QuestDetailFragmentDirections.actionQuestDetailFragmentToQuestFragment())
        }

        binding.btnRepeat.setOnClickListener {
            viewModel.uiScope.launch {
                viewModel.ioScope.launch {
                    viewModel.resetQuest(quest.id)
                }
            }
            findNavController().navigate(QuestDetailFragmentDirections.actionQuestDetailFragmentToQuestFragment())
        }
        return binding.root
    }
}
