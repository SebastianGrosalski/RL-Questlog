package com.example.questtask.ui.quest

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.questtask.repository.PreferenceProvider
import com.example.questtask.repository.QuestRepository
import com.example.questtask.repository.room.Quest
import com.example.questtask.repository.room.QuestDao
import com.example.questtask.repository.room.QuestDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class QuestViewModel(application : Application) : AndroidViewModel(application) {
    private val repository : QuestRepository
    init{
        val questDao = QuestDatabase.getInstance(application).questDatabaseDao
        repository = QuestRepository(questDao)
    }

    private val _navigate = MutableLiveData<Boolean>()
    val navigate : LiveData<Boolean>
        get() = _navigate

    private val _xp = MutableLiveData<String>()
    val xp : LiveData<String>
    get() = _xp

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

    fun levelRatioStringOf(pointsKey : String, levelKey : String) : String{
              _xp.value = "${prefProvider.getPoints(pointsKey)}/" +
                "${prefProvider.calculateLevelBarrier(levelKey)}"
              return _xp.value!!
    }

    fun setQuests(){
        questList = prefProvider.getTopicsList()
        uiScope.launch {
            ioScope.launch {
                _quests.postValue(repository.questByTopics(questList))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}