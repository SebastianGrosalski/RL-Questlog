package com.example.questtask.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.questtask.util.*

private const val NAME = "name"
private const val CONTAINS_PREFERENCES = "containsPreferences"

class PreferenceProvider(context: Context) {
    private val applicationContext: Context = context.applicationContext
    private val sharedPreferences : SharedPreferences = applicationContext.getSharedPreferences(
        "preferences",
        Context.MODE_PRIVATE
    )
    fun putName(string: String){
        sharedPreferences.edit().putString(
            NAME,
            string
        ).apply()
    }

    fun putPreferredTopics(topicMap : HashMap<String, Boolean>){
        for(s : String in topicMap.keys)
        {
            sharedPreferences.edit().putBoolean(s, topicMap[s]!!).apply()
        }
    }

    fun putContainsFlag(){
        sharedPreferences.edit().putBoolean(
            CONTAINS_PREFERENCES,
            true
        ).apply()
    }

    fun getContainsFlag() : Boolean{
        return sharedPreferences.getBoolean(CONTAINS_PREFERENCES, false)
    }

    fun getName() : String?{
        return sharedPreferences.getString(NAME, "Fehler")
    }

    fun getTidiness() : Boolean?{
        return sharedPreferences.getBoolean(TIDINESS, false)
    }

    fun getWork() : Boolean?{
        return sharedPreferences.getBoolean(WORK, false)
    }

    fun getHealth() : Boolean?{
        return sharedPreferences.getBoolean(HEALTH, false)
    }

    fun getFitness() : Boolean?{
        return sharedPreferences.getBoolean(FITNESS, false)
    }

    fun getDiet() : Boolean?{
        return sharedPreferences.getBoolean(DIET, false)
    }

    fun getKnowledge() : Boolean{
        return sharedPreferences.getBoolean(KNOWLEDGE, false)
    }
}