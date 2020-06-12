package com.example.questtask.repository

import android.content.Context
import android.content.SharedPreferences

private const val NAME = "name"
private const val CONTAINS_PREFERENCES = "containsPreferences"

class PreferenceProvider(context: Context) {
    val applicationContext = context.applicationContext
    val sharedPreferences : SharedPreferences = applicationContext.getSharedPreferences(
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
}