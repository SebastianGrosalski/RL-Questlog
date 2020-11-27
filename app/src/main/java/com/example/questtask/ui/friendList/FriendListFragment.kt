package com.example.questtask.ui.friendList

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.questtask.R
import com.example.questtask.databinding.FriendListFragmentBinding
import com.example.questtask.ui.donequests.DoneQuestsViewModel
import com.example.questtask.ui.friendList.recyclerview.FriendListAdapter
import kotlinx.coroutines.launch

class FriendListFragment : Fragment() {

    companion object {
        fun newInstance() = FriendListFragment()
    }

    private lateinit var viewModel: FriendListViewModel
    private lateinit var binding: FriendListFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(FriendListViewModel::class.java)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.friend_list_fragment,
            container,
            false
        )

       var adapter = FriendListAdapter()
        viewModel.uiScope.launch {
        viewModel.getFriends().observe(viewLifecycleOwner, Observer {
            adapter.data = it
        })
        }
        binding.friendList.adapter = adapter

        return binding.root
    }

}