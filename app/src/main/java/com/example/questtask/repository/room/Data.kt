package com.example.questtask.repository.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import org.threeten.bp.OffsetDateTime
import java.util.*

@Entity(tableName = "quest_table")
data class Quest(
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    var title : String,
    val description_short : String,
    val description_long : String,
    val topic : String,
    @ColumnInfo(defaultValue = "1") val difficulty : Int,
    val startDate : OffsetDateTime? = null,
    val endDate : OffsetDateTime? = null,
    @ColumnInfo(defaultValue = "0") val done : Boolean,
    @ColumnInfo(defaultValue = "0") val accepted : Boolean
)
