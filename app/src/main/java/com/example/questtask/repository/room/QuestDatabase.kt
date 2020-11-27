package com.example.questtask.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Quest::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)

abstract class QuestDatabase : RoomDatabase(){
abstract val questDatabaseDao : QuestDao
    companion object {
        @Volatile
        private var INSTANCE: QuestDatabase? = null

        fun getInstance(context: Context): QuestDatabase{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        QuestDatabase::class.java,
                        "quest_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}