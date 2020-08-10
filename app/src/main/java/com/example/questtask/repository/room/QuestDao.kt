package com.example.questtask.repository.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuestDao {
    @Insert
    fun insert(quest: Quest)

    @Query("UPDATE quest_table SET accepted = 1 WHERE id LIKE (:id)")
    fun setAccepted(id: Int)

    @Query("UPDATE quest_table SET done = 0, accepted = 0 WHERE id LIKE(:id)")
    fun resetQuest(id: Int)

    @Query("UPDATE quest_table SET done = 1 WHERE id LIKE (:id)")
    fun setDone(id: Int)

    @Insert
    fun insertAll(questList: List<Quest>)

    @Query("SELECT * FROM quest_table")
    fun getAllQuests(): LiveData<List<Quest>>

    @Query("SELECT * FROM quest_table WHERE accepted = 0")
    fun getUnaccepted() : LiveData<List<Quest>>

    @Query("SELECT * FROM quest_table WHERE accepted = 1")
    fun getAccepted() : LiveData<List<Quest>>

    @Query("SELECT * FROM quest_table WHERE topic IN(:topics) AND done = 0 ORDER BY accepted DESC, topic DESC")
    fun questByTopics(topics: List<String>): List<Quest>

    @Query("SELECT * FROM quest_table WHERE topic in(:topics) AND done = 1 ORDER BY topic DESC")
    fun questByTopicsDone(topics: List<String>): List<Quest>

    @Query("SELECT * FROM quest_table WHERE id LIKE (:id)")
    fun questById(id: Int) : Quest

}