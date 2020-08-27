package com.example.questtask.repository

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
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
        ).commit()
        sharedPreferences.edit().clear()
    }

    fun getPoints(key: String) : Int{
        return sharedPreferences.getInt(key, 0)
    }

    fun putPoints(levelKey : String, pointsKey : String, points: Int){
        val oldPoints : Int = sharedPreferences.getInt(pointsKey, 0)
        var newPoints = oldPoints + points
        var levelBarrier = calculateLevelBarrier(levelKey)
        if(newPoints >= levelBarrier){
            newPoints %= levelBarrier
            levelUp(levelKey)
        }
        sharedPreferences.edit().putInt(
            pointsKey,
            newPoints
        ).commit()
    }

    fun calculateLevelBarrier(key : String) : Int{
        return when(sharedPreferences.getInt(key, 0)){
            in 0..4 -> LEVEL_BARRIER_ROOKIE
            in 5..9 -> LEVEL_BARRIER_BEGINNER
            in 10..14 -> LEVEL_BARRIER_INTERMEDIATE
            in 15..19 -> LEVEL_BARRIER_EXPERT
            else -> LEVEL_BARRIER_MASTER
        }
    }

    fun levelUp(key: String){
        val oldLevel = sharedPreferences.getInt(key, 0)
        sharedPreferences.edit().putInt(key, oldLevel+1).commit()
    }

    fun putPreferredTopics(topicMap : HashMap<String, Boolean>){
        for(s : String in topicMap.keys)
        {
            sharedPreferences.edit().putBoolean(s, topicMap[s]!!).commit()
        }
    }

    fun putContainsFlag(){
        sharedPreferences.edit().putBoolean(
            CONTAINS_PREFERENCES,
            true
        ).commit()
    }

    fun levelAll(){
        levelUp(LEVEL)
        levelUp(DIET_LVL)
        levelUp(KNOWLEDGE_LVL)
        levelUp(FITNESS_LVL)
        levelUp(TIDINESS_LVL)
        levelUp(WORK_LVL)
        levelUp(HEALTH_LVL)
    }

    fun getLevel(key : String) : Int{
        return sharedPreferences.getInt(key, 0)
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