package com.example.questtask.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.example.questtask.util.*

private const val NAME = "name"
private const val POINTS = "points"
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

    fun getPoints() : Int{
        return sharedPreferences.getInt(POINTS, 0)
    }

    fun putPoints(points: Int){
        val oldPoints : Int = sharedPreferences.getInt(POINTS, 0)
        val newPoints = oldPoints + points
        sharedPreferences.edit().putInt(
            POINTS,
            newPoints
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

    fun getTopicsList() : MutableList<String> {
        val topicsList: MutableList<String> = mutableListOf()
        if (getTidiness()!!) {
            topicsList.add(TIDINESS)
        }
        if(getDiet()!!){
            topicsList.add(DIET)
        }
        if(getKnowledge()!!){
            topicsList.add(KNOWLEDGE)
        }
        if(getFitness()!!){
            topicsList.add(FITNESS)
        }
        if(getHealth()!!){
            topicsList.add(HEALTH)
        }
        if(getWork()!!){
            topicsList.add(WORK)
        }
        return topicsList
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