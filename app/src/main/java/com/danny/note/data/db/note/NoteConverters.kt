package com.danny.note.data.db.note

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.danny.note.data.model.Color
import com.google.gson.Gson

class NoteConverters {
    @TypeConverter
    fun fromTags(tag : List<Color>) : String{
        return Gson().toJson(tag)
    }
    @TypeConverter
    fun toTags(json : String) : List<Color> {
        return Gson().fromJson(json, Array<Color>::class.java).toList()
    }
}