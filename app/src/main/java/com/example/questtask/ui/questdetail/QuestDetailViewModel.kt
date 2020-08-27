package com.example.questtask.ui.questdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.questtask.repository.PreferenceProvider
import com.example.questtask.repository.QuestRepository
import com.example.questtask.repository.room.Quest
import com.example.questtask.repository.room.QuestDatabase
import kotlinx.coroutines.*
import com.example.questtask.util.*

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

    suspend fun questDone(id: Int, topic : String, pts: Int){
        prefProvider.putPoints(LEVEL, POINTS, pts)
        when(topic){
            KNOWLEDGE -> prefProvider.putPoints(KNOWLEDGE_LVL, KNOWLEDGE_POINTS, pts)
            DIET -> prefProvider.putPoints(DIET_LVL, DIET_POINTS, pts)
            TIDINESS -> prefProvider.putPoints(TIDINESS_LVL, TIDINESS_POINTS, pts)
            FITNESS -> prefProvider.putPoints(FITNESS_LVL, FITNESS_POINTS, pts)
            WORK -> prefProvider.putPoints(WORK_LVL, WORK_POINTS, pts)
            else -> prefProvider.putPoints(HEALTH_LVL, HEALTH_POINTS, pts)
        }
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






