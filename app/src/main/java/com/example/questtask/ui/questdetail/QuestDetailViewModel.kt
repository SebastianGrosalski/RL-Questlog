package com.example.questtask.ui.questdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.questtask.repository.PreferenceProvider
import com.example.questtask.repository.QuestRepository
import com.example.questtask.repository.room.Quest
import com.example.questtask.repository.room.QuestDatabase
import kotlinx.coroutines.*

class QuestDetailViewModel(application: Application, idParam : Int) : AndroidViewModel(application)
{
    val prefProvider = PreferenceProvider(application.applicationContext)

    private val repository: QuestRepository
    private var viewModelJob = Job()
    val id = idParam
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    var quest: Quest

    init {
        val questDao = QuestDatabase.getInstance(application).questDatabaseDao
        repository = QuestRepository(questDao)
        quest = runBlocking { loadQuest() }
        }

    private suspend fun loadQuest() : Quest =
        withContext(ioScope.coroutineContext) {
            repository.questById(id)
        }

    suspend fun updateAccepted(id: Int){
        repository.setAccepted(id)
    }

    suspend fun questDone(id: Int, pts: Int){
        prefProvider.putPoints(pts)
        repository.setDone(id)
    }

    suspend fun resetQuest(id: Int){
        repository.resetQuest(id)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}






