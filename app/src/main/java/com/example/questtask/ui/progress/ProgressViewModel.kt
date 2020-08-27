package com.example.questtask.ui.progress

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.questtask.repository.PreferenceProvider

class ProgressViewModel(application : Application) : AndroidViewModel(application) {
    val prefProvider = PreferenceProvider(application.applicationContext)

}