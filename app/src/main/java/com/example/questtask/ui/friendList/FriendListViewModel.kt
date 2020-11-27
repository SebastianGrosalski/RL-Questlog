package com.example.questtask.ui.friendList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.questtask.repository.classes.Friend
import com.example.questtask.repository.classes.FriendRequest
import com.example.questtask.repository.firebase.FirebaseRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class FriendListViewModel : ViewModel() {
    private var viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    private val mAuth = Firebase.auth
    val friendRequests = MutableLiveData<MutableList<Friend>>()

    suspend fun getFriends() : LiveData<MutableList<Friend>> {
        var tmpFriends = mutableListOf<Friend>()
        tmpFriends = FirebaseRepository.getAllFriends()
        friendRequests.postValue(tmpFriends)
        return friendRequests
    }


}