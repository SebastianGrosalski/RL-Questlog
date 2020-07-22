package com.example.questtask.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.questtask.repository.PreferenceProvider

class SettingsViewModel(application : Application) : AndroidViewModel(application) {
    val prefProvider = PreferenceProvider(application.applicationContext)
}