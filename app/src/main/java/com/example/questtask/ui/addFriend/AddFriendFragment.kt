package com.example.questtask.ui.addFriend

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.questtask.R
import com.example.questtask.databinding.AddFriendFragmentBinding
import com.example.questtask.databinding.FragmentInitialPreferencesBinding
import com.example.questtask.repository.firebase.FirebaseRepository
import com.example.questtask.ui.addFriend.recyclerview.RequestAdapter
import com.example.questtask.ui.initialPreferences.InitialPreferencesViewModel
import kotlinx.coroutines.launch

class AddFriendFragment : Fragment() {

    companion object {
        fun newInstance() = AddFriendFragment()
    }

    private lateinit var viewmodel: AddFriendViewModel
    private lateinit var binding: AddFriendFragmentBinding
    private lateinit var adapter: RequestAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewmodel = ViewModelProvider(this).get(AddFriendViewModel::class.java)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.add_friend_fragment,
            container,
            false
        )

        adapter = RequestAdapter()

        viewmodel.toasty.observe(viewLifecycleOwner, Observer {
            if(it != null){
            if(it){
                Toast.makeText(context, "Freundschaftsanfrage versendet!", Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(context, "MIAUUUUU NEIN", Toast.LENGTH_SHORT).show()
            }
        })
        binding.pendingRequests.adapter = adapter
        binding.btnConfirm.setOnClickListener{
            if(containsEmail()) {
                viewmodel.ioScope.launch {
                    viewmodel.sendFriendRequest(binding.etFriend.text.toString().trim())
                    }
                }
            }

        return binding.root
    }
    private fun containsEmail(): Boolean{
        return (binding.etFriend.text.toString().trim().isNotBlank() ||
                binding.etFriend.text.toString().trim().isNotEmpty())
    }

    override fun onStart() {
        super.onStart()
        viewmodel.getReceivedFriendRequests().observe(viewLifecycleOwner, Observer {
            it.let {
                adapter.data = it
            }
        }
        )
    }
}