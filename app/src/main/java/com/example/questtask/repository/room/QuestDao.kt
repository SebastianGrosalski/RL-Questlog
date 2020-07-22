package com.example.questtask.repository.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuestDao {
    @Insert
    fun insert(quest: Quest)

    @Query("SELECT * FROM quest_table")
    fun getAll(): LiveData<List<Quest>>

    @Query("SELECT * FROM quest_table WHERE accepted = 0")
    fun getUnaccepted() : LiveData<List<Quest>>

    @Query("SELECT * FROM quest_table WHERE accepted = 1")
    fun getAccepted() : LiveData<List<Quest>>

    @Query("SELECT * FROM quest_table WHERE topic LIKE :topic")
    fun getTopic(topic : String) : LiveData<List<Quest>>
}