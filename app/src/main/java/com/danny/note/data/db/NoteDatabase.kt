package com.danny.note.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.danny.note.data.db.color.ColorDAO
import com.danny.note.data.db.note.NoteConverters
import com.danny.note.data.db.note.NoteDAO
import com.danny.note.data.model.Color
import com.danny.note.data.model.Note

@Database(entities = [Color::class, Note::class], version = 1, exportSchema = false)
@TypeConverters(NoteConverters::class)
abstract class NoteDatabase : RoomDatabase(){
    abstract fun getColorDAO() : ColorDAO
    abstract fun getNoteDAO() : NoteDAO
}