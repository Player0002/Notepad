package com.danny.note.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.danny.note.data.model.Color

@Database(entities = [Color::class], version = 1, exportSchema = false)
abstract class ColorDatabase : RoomDatabase(){
    abstract fun getColorDAO() : ColorDAO
}