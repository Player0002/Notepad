package com.danny.note.data.repository.dataSource

import com.danny.note.data.model.Color
import com.danny.note.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteDataSource {
    suspend fun saveNote(note : Note)
    suspend fun deleteNote(note : Note)
    fun getAllNotes(): Flow<List<Note>>
}