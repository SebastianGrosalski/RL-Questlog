package com.example.questtask.ui.initialPreferences

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.CheckBox
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.Preference
import com.example.questtask.R
import com.example.questtask.repository.PreferenceProvider

class InitialPreferencesViewModel(application: Application) : AndroidViewModel(application) {
    private val _navigate = MutableLiveData<Boolean>()
    val navigate : LiveData<Boolean>
    get() = _navigate

    private val topics = HashMap<String, Boolean>()
    private val prefProvider : PreferenceProvider = PreferenceProvider(
        application.applicationContext
    )

    fun navigate(){
        _navigate.value = true
    }

    fun doneNavigating(){
        _navigate.value = null
    }

    //Puts the selected Topics into Shared preferences
    //and sets the flag to determine whether preferences contain values
    fun putTopics(map : HashMap<String, Boolean>){
        prefProvider.putPreferredTopics(map)
        prefProvider.putContainsFlag()
    }
}
