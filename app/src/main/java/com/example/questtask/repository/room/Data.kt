package com.example.questtask.repository.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import org.threeten.bp.OffsetDateTime
import java.io.Serializable
import java.util.*

@Entity(tableName = "quest_table")
data class Quest(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var firestoreId : String? = "",
    var from: String = "",
    var title : String? = "",
    val description_short : String? = "",
    val description_long : String? = "",
    val topic : String? = "",
    val difficulty : Int? = 0,
    var done : Boolean? = false,
    var accepted : Boolean? = false
) : Serializable
