package com.danny.note.domain.usecase.note

import com.danny.note.data.model.Color
import com.danny.note.data.model.Note
import com.danny.note.domain.repository.ColorRepository
import com.danny.note.domain.repository.NoteRepository

class SaveNoteUseCase(private val noteRepository: NoteRepository) {
    suspend fun execute(note : Note) = noteRepository.saveNote(note)
}