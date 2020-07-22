package com.example.questtask.ui.quest

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.questtask.repository.PreferenceProvider
import com.example.questtask.repository.room.QuestDao

class QuestViewModel(val database : QuestDao,
                     application : Application
                     ) : AndroidViewModel(application) {

    val prefProvider = PreferenceProvider(application.applicationContext)
}