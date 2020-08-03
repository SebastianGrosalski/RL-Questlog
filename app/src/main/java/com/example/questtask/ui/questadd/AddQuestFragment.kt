package com.example.questtask.ui.questadd

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.questtask.R

class AddQuestFragment : Fragment() {

    companion object {
        fun newInstance() = AddQuestFragment()
    }

    private lateinit var viewModel: AddQuestViewmodel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_quest, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddQuestViewmodel::class.java)
        // TODO: Use the ViewModel
    }

}
