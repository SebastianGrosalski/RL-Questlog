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
import com.example.questtask.repository.QuestRepository
import com.example.questtask.repository.firebase.FirebaseRepository
import com.example.questtask.repository.room.Quest
import com.example.questtask.repository.room.QuestDatabase
import com.example.questtask.util.*
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class InitialPreferencesViewModel(application: Application) : AndroidViewModel(application) {
    private val _navigate = MutableLiveData<Boolean>()
    val navigate : LiveData<Boolean>
    get() = _navigate
    val repository : QuestRepository
    private val firebaseRepo: FirebaseRepository = FirebaseRepository

    init {
        val questDao = QuestDatabase.getInstance(application).questDatabaseDao
        repository = QuestRepository(questDao)
    }

    private val prefProvider : PreferenceProvider = PreferenceProvider(
        application.applicationContext
    )

    fun navigate(){
        _navigate.value = true
    }

    fun doneNavigating(){
        _navigate.value = null
    }

    private var viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    //Puts the selected Topics into Shared preferences
    //and sets the flag to determine whether preferences contain values
    fun putTopics(map : HashMap<String, Boolean>){
        prefProvider.putPreferredTopics(map)
        prefProvider.putContainsFlag()
        if(!prefProvider.getContainsFlag())
            prefProvider.levelAll()
    }

    fun setInitialQuests(){
        firebaseRepo.getInitialQuestRef().addOnSuccessListener {querySnapshot ->
            for(documentSnapshot in querySnapshot){
                val newQuest = documentSnapshot.toObject(Quest::class.java)
                uiScope.launch {
                    ioScope.launch {
                        repository.insert(newQuest)
                    }
                }
            }
        }
    }

    fun loadToFirestore(map: HashMap<String, Boolean>){
        firebaseRepo.setPreferredTopics(map)
    }
}
