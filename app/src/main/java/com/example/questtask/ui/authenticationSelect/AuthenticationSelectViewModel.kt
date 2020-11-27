package com.example.questtask.ui.authenticationSelect

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.preference.Preference
import com.example.questtask.repository.PreferenceProvider

class AuthenticationSelectViewModel(application: Application) : AndroidViewModel(application) {
    val prefProvider = PreferenceProvider(application.applicationContext)
}