package com.danny.note.data.db.note

import androidx.room.*
import com.danny.note.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNote(note : Note)

    @Delete
    suspend fun deleteNote(note : Note)

    @Query("SELECT * FROM note ORDER BY id DESC")
    fun getAllNotes() : Flow<List<Note>>
}