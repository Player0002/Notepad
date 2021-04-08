package com.danny.note.domain.repository

import com.danny.note.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes() : Flow<List<Note>>
    suspend fun saveNote(note : Note)
    suspend fun deleteNote(note : Note)
}