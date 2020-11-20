package com.example.questtask.ui.authenticationEmail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.questtask.repository.PreferenceProvider

class AuthenticationEmailViewModel(application: Application) : AndroidViewModel(application) {
    val prefProvider : PreferenceProvider = PreferenceProvider(application.applicationContext)
}