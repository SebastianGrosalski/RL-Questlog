package com.example.questtask.repository.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import org.threeten.bp.OffsetDateTime
import java.util.*

@Entity(tableName = "quest_table")
data class Quest(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var title : String?,
    val description_short : String?,
    val description_long : String?,
    val topic : String?,
    val difficulty : Int?,
    val startDate : OffsetDateTime? = null,
    val endDate : OffsetDateTime? = null,
    val done : Boolean?,
    val accepted : Boolean?
)
