package com.example.questtask.ui.quest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.questtask.R
import com.example.questtask.databinding.FragmentQuestsBinding
import com.example.questtask.repository.room.QuestDatabase
import com.example.questtask.ui.quest.recyclerview.QuestAdapter
import com.example.questtask.ui.quest.recyclerview.QuestListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class QuestFragment : Fragment() {

    private lateinit var viewModel: QuestViewModel
    private lateinit var binding: FragmentQuestsBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.show()
        val navBar: BottomNavigationView = (activity as AppCompatActivity).findViewById(R.id.nav_view)
        navBar.isVisible = true
        val application = requireNotNull(this.activity).application
        val dataSource = QuestDatabase.getInstance(application).questDatabaseDao
        val viewModelFactory = QuestViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(QuestViewModel::class.java)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_quests,
            container,
            false
        )

        binding.tvName.text = viewModel.prefProvider.getName()
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

        viewModel.navigate.observe(viewLifecycleOwner, Observer{
            if(findNavController().currentDestination?.id == R.id.questFragment && it){
                findNavController().navigate(QuestFragmentDirections
                    .actionNavigationHomeToAddQuest())
                viewModel.doneNavigating()
            }
        })

        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer { quest ->
            quest?.let {
                this.findNavController().navigate(QuestFragmentDirections.actionQuestFragmentToQuestDetailFragment(quest))
                viewModel.onQuestClickedDone()
            }
        })

        return binding.root
    }
}
