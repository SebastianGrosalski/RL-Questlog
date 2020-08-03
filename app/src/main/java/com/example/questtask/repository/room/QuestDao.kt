package com.example.questtask.repository.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuestDao {
    @Insert
    fun insert(quest: Quest)

    @Insert
    fun insertAll(questList: List<Quest>)

    @Query("SELECT * FROM quest_table")
    fun getAllQuests(): LiveData<List<Quest>>

    @Query("SELECT * FROM quest_table WHERE accepted = 0")
    fun getUnaccepted() : LiveData<List<Quest>>

    @Query("SELECT * FROM quest_table WHERE accepted = 1")
    fun getAccepted() : LiveData<List<Quest>>

    @Query("SELECT * FROM quest_table WHERE topic IN(:topics)")
    fun questByTopics(topics: List<String>): List<Quest>

    @Query("SELECT * FROM quest_table WHERE id LIKE (:id)")
    fun questById(id: Int) : Quest

}