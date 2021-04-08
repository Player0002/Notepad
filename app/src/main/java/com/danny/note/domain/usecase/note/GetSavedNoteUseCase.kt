package com.danny.note.domain.usecase.note

import com.danny.note.data.model.Color
import com.danny.note.data.model.Note
import com.danny.note.domain.repository.ColorRepository
import com.danny.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNoteUseCase(private val noteRepository: NoteRepository) {
    fun execute() : Flow<List<Note>> = noteRepository.getNotes()
}