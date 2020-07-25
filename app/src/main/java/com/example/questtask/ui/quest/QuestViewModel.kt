package com.example.questtask.ui.quest

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.questtask.repository.PreferenceProvider
import com.example.questtask.repository.room.QuestDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class QuestViewModel(val database : QuestDao,
                     application : Application
                     ) : AndroidViewModel(application) {

    val prefProvider = PreferenceProvider(application.applicationContext)

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val quests = database.getAllQuests()

    init{

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}