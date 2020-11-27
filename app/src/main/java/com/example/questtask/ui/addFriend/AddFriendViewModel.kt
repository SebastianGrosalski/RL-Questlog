package com.example.questtask.ui.addFriend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.questtask.repository.classes.FriendRequest
import com.example.questtask.repository.firebase.FirebaseAuthenticate
import com.example.questtask.repository.firebase.FirebaseRepository
import com.example.questtask.repository.room.Quest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AddFriendViewModel : ViewModel() {
        private var viewModelJob = Job()
        val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
        val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)
        val mAuth = Firebase.auth
        private val friendRequests = MutableLiveData<MutableList<FriendRequest>>()
        val toasty = MutableLiveData<Boolean?>().apply { value = null }

        suspend fun sendFriendRequest(email: String){
                if(!FirebaseRepository.doesRequestExist(FirebaseRepository.getIdByEmail(email))){
                        toasty.postValue(false)
                } else {
                        FirebaseRepository.sendFriendRequest(email)
                        toasty.postValue(true)
                }
        }

        fun getReceivedFriendRequests() : LiveData<MutableList<FriendRequest>> {
                var requestList : MutableList<FriendRequest> = mutableListOf()
                FirebaseRepository.getReceivedFriendRequests().addOnSuccessListener { querySnapshot ->
                        for(request in querySnapshot) {
                                val tmp = request.toObject(FriendRequest::class.java)
                                requestList.add(tmp)
                        }
                        friendRequests.postValue(requestList)
                }
                return friendRequests
        }
}