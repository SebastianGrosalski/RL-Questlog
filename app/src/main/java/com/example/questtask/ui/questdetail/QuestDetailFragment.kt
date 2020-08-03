package com.example.questtask.ui.questdetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs

import com.example.questtask.R
import com.example.questtask.databinding.FragmentQuestDetailBinding

class QuestDetailFragment : Fragment() {

    private lateinit var binding : FragmentQuestDetailBinding
    private lateinit var viewModel: QuestDetailViewModel

    companion object {
        fun newInstance() = QuestDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val args = arguments?.let { QuestDetailFragmentArgs.fromBundle(it) }
        val questId = args?.questId
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_quest_detail
            ,
            container,
            false
        )
        viewModel = ViewModelProvider(this).get(QuestDetailViewModel::class.java)
        binding.textView2.text = questId.toString()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QuestDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
