package com.danny.note.data.repository

import com.danny.note.data.model.Note
import com.danny.note.data.repository.dataSource.NoteDataSource
import com.danny.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val noteDataSource: NoteDataSource) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> = noteDataSource.getAllNotes()

    override suspend fun saveNote(note: Note) {
        noteDataSource.saveNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDataSource.deleteNote(note)
    }
}