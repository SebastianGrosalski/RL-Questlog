package com.example.questtask.ui.hello

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.questtask.MainActivity
import com.example.questtask.repository.PreferenceProvider

class HelloViewModel(application: Application) : AndroidViewModel(application) {
    val prefProvider : PreferenceProvider = PreferenceProvider(application.applicationContext)

    private val _navigateToInitialPreferences = MutableLiveData<Boolean>()
    val navigateToInitialPreferences:  LiveData<Boolean>
    get() = _navigateToInitialPreferences


    fun setText(string : String){
        prefProvider.putName(string)
    }

    fun navigate(){
        _navigateToInitialPreferences.value = true
    }

    fun doneNavigating(){
        _navigateToInitialPreferences.value = null
    }
}