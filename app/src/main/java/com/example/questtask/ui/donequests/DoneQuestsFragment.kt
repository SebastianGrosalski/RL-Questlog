package com.example.questtask.ui.donequests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.questtask.R
import com.example.questtask.databinding.FragmentDoneQuestsBinding
import com.example.questtask.ui.quest.recyclerview.QuestAdapter
import com.example.questtask.ui.quest.recyclerview.QuestListener
import com.example.questtask.util.LEVEL
import com.example.questtask.util.POINTS
import kotlinx.coroutines.launch

class DoneQuestsFragment : Fragment() {

    private lateinit var viewModel: DoneQuestsViewModel
    private lateinit var binding: FragmentDoneQuestsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(DoneQuestsViewModel::class.java)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_done_quests,
            container,
            false
        )
        //Set values for the progressbar under the appbar
        binding.progressXp.max = viewModel.prefProvider.calculateLevelBarrier(LEVEL)
        binding.progressXp.progress = viewModel.prefProvider.getPoints(POINTS)
        binding.tvLvlPoints.text = viewModel.prefProvider.getLevel(LEVEL).toString()
        binding.tvName.text = viewModel.prefProvider.getName()
        binding.tvPoints.text = viewModel.levelRatioStringOf(POINTS, LEVEL)

        //Viewmodel
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        //Recyclerview
        val adapter = QuestAdapter(QuestListener{ questId -> viewModel.onQuestClicked(questId)})
        binding.questView.adapter = adapter

        viewModel.xp.observe(viewLifecycleOwner, Observer{
            binding.tvPoints.text = it
        })

        viewModel.quests.observe(viewLifecycleOwner, Observer{
            viewModel.uiScope.launch { viewModel.setQuests() }
            it?.let {
                adapter.submitList(it)
            }
        })

        //Navigation back to Detail
        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer { quest ->
            quest?.let {
                this.findNavController().navigate(DoneQuestsFragmentDirections.actionDoneQuestsFragmentToQuestDetailFragment(quest))
                viewModel.onQuestClickedDone()
            }
        })

        return binding.root
    }
}
