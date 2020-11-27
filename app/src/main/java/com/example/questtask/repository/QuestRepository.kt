package com.example.questtask.repository

import androidx.lifecycle.LiveData
import com.example.questtask.repository.room.Quest
import com.example.questtask.repository.room.QuestDao

class QuestRepository(private val dao: QuestDao) {
        fun insert(quest: Quest){
            dao.insert(quest)
        }

        fun questByTopics(topics: List<String>) : List<Quest>{
            return dao.questByTopics(topics)
        }

        fun questByTopicsDone(topics: List<String>) : List<Quest>{
            return dao.questByTopicsDone(topics)
        }

        fun resetQuest(id: Int){
            dao.resetQuest(id)
        }

        fun questById(id: Int) : Quest{
            return dao.questById(id)
        }

        fun setAccepted(id: Int){
            dao.setAccepted(id)
        }

        fun setDone(id: Int){
            dao.setDone(id)
        }

        fun dropQuests(){
            dao.nukeTable()
        }
}