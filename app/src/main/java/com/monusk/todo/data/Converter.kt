package com.monusk.todo.data

import androidx.room.TypeConverter
import com.monusk.todo.data.models.Priority

class Converter {

    @TypeConverter
    fun fromPriorityToString(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun fromStringToPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}
