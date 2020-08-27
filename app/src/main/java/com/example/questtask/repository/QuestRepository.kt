package com.example.questtask.repository

import androidx.lifecycle.LiveData
import com.example.questtask.repository.room.Quest
import com.example.questtask.repository.room.QuestDao

class QuestRepository(private val dao: QuestDao) {
        suspend fun insert(quest: Quest){
            dao.insert(quest)
        }

        suspend fun questByTopics(topics: List<String>) : List<Quest>{
            return dao.questByTopics(topics)
        }

        suspend fun questByTopicsDone(topics: List<String>) : List<Quest>{
            return dao.questByTopicsDone(topics)
        }

        suspend fun resetQuest(id: Int){
            dao.resetQuest(id)
        }

        fun questById(id: Int) : Quest{
            return dao.questById(id)
        }

        suspend fun setAccepted(id: Int){
            dao.setAccepted(id)
        }

        suspend fun setDone(id: Int){
            dao.setDone(id)
        }
}