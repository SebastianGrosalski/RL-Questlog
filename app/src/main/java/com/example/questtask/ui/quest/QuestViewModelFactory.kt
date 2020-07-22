package com.example.questtask.ui.quest

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.questtask.repository.room.QuestDao
import java.lang.IllegalArgumentException

class QuestViewModelFactory(private val dataSource: QuestDao,
                            private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(QuestViewModel::class.java)){
            return QuestViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}