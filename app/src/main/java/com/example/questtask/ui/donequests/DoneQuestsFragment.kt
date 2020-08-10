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

        binding.tvName.text = viewModel.prefProvider.getName()
        binding.tvPoints.text = viewModel.prefProvider.getPoints().toString()
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        val adapter = QuestAdapter(QuestListener{ questId -> viewModel.onQuestClicked(questId)})
        binding.questView.adapter = adapter

        viewModel.quests.observe(viewLifecycleOwner, Observer{
            viewModel.uiScope.launch { viewModel.setQuests() }
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer { quest ->
            quest?.let {
                this.findNavController().navigate(DoneQuestsFragmentDirections.actionDoneQuestsFragmentToQuestDetailFragment(quest))
                viewModel.onQuestClickedDone()
            }
        })

        return binding.root
    }
}
