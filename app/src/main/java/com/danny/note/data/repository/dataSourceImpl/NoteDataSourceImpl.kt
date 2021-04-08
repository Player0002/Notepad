package com.danny.note.data.repository.dataSourceImpl

import com.danny.note.data.db.color.ColorDAO
import com.danny.note.data.db.note.NoteDAO
import com.danny.note.data.model.Color
import com.danny.note.data.model.Note
import com.danny.note.data.repository.dataSource.ColorDataSource
import com.danny.note.data.repository.dataSource.NoteDataSource
import kotlinx.coroutines.flow.Flow

class NoteDataSourceImpl (private val noteDAO: NoteDAO) : NoteDataSource {
    override suspend fun saveNote(note: Note) {
        noteDAO.saveNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDAO.deleteNote(note)
    }

    override fun getAllNotes(): Flow<List<Note>> = noteDAO.getAllNotes()
}