package com.example.questtask.ui.questadd

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.questtask.repository.QuestRepository
import com.example.questtask.repository.room.Quest
import com.example.questtask.repository.room.QuestDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class AddQuestViewModel(application: Application) : AndroidViewModel(application) {
    val repository : QuestRepository
    //lateinit var quest : Quest

    var questTopic = ""
    var questDifficulty = 0
    var questTitle = ""
    var questShortDesc = ""
    var questLongDesc = ""

    private val _navigate = MutableLiveData<Boolean>()
    val navigate : LiveData<Boolean>
        get() = _navigate

    fun navigate(){
        _navigate.value = true
    }

    fun doneNavigating(){
        _navigate.value = false
    }


    private var viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    init {
        val questDao = QuestDatabase.getInstance(application).questDatabaseDao
        repository = QuestRepository(questDao)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    suspend fun addQuest(quest: Quest){
        repository.insert(quest)
    }
}
