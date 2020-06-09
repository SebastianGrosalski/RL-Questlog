package com.example.questtask.ui.hello

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HelloViewModel : ViewModel() {
    private val _navigateToInitialPreferences = MutableLiveData<Boolean>()
    val navigateToInitialPreferences:  LiveData<Boolean>
    get() = _navigateToInitialPreferences

    fun navigate(){
        _navigateToInitialPreferences.value = true
    }

    fun doneNavigating(){
        _navigateToInitialPreferences.value = false
    }
}