package com.example.questtask.ui.quest

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.questtask.repository.PreferenceProvider
import com.example.questtask.repository.room.Quest
import com.example.questtask.repository.room.QuestDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class QuestViewModel(
    private val database : QuestDao,
    application : Application
                     ) : AndroidViewModel(application) {

    private val _navigate = MutableLiveData<Boolean>()
    val navigate : LiveData<Boolean>
        get() = _navigate

    private val _navigateToDetail = MutableLiveData<Int>()
    val navigateToDetail
    get() = _navigateToDetail

    val prefProvider = PreferenceProvider(application.applicationContext)

    private val _quests = MutableLiveData<List<Quest>>()
    val quests : LiveData<List<Quest>>
    get() = _quests

    private var viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    var questList =  prefProvider.getTopicsList()

    init{
       uiScope.launch { setQuests() }
    }

    fun navigate(){
        _navigate.value = true
    }

    fun doneNavigating(){
        _navigate.value = false
    }

    fun onQuestClicked(id : Int){
        _navigateToDetail.value = id
    }

    fun onQuestClickedDone(){
        _navigateToDetail.value = null
    }

    fun setQuests(){
        questList = prefProvider.getTopicsList()
        uiScope.launch {
            ioScope.launch {
                _quests.postValue(database.questByTopics(questList))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}