package com.danny.note.data.db.note

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.danny.note.data.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(NoteConverters::class)
abstract class NoteDatabase : RoomDatabase(){
    abstract fun getNoteDAO() : NoteDAO
}