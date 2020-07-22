package com.example.questtask.repository.room
import android.os.Build
import androidx.annotation.RequiresApi
import org.threeten.bp.LocalDateTime
import androidx.room.TypeConverter
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

object Converters {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String?) : OffsetDateTime? {
        return value?.let {
            return formatter.parse(value, OffsetDateTime::from)
        }
    }
    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(date: OffsetDateTime?) : String?{
        return date?.format(formatter)
    }
}