package com.example.questtask.ui.questdetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.questtask.repository.room.QuestDao
import java.lang.IllegalArgumentException

class QuestDetailViewModelFactory(private val application: Application,
                            private val idParam: Int) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(QuestDetailViewModel::class.java)){
            return QuestDetailViewModel(application, idParam) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }}