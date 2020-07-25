package com.example.questtask.ui.quest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.questtask.R
import com.example.questtask.databinding.FragmentQuestsBinding
import com.example.questtask.repository.room.QuestDatabase
import com.example.questtask.ui.quest.recyclerview.QuestAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

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
        val adapter = QuestAdapter()
        binding.questView.adapter = adapter
        viewModel.quests.observe(viewLifecycleOwner, Observer{
            it?.let {
                adapter.data = it
            }
        })
        return binding.root
    }
}
