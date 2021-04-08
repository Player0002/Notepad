package com.danny.note.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.danny.note.domain.usecase.color.DeleteColorUseCase
import com.danny.note.domain.usecase.color.GetSavedColorUseCase
import com.danny.note.domain.usecase.color.SaveColorUseCase
import com.danny.note.domain.usecase.note.DeleteNoteUseCase
import com.danny.note.domain.usecase.note.GetSavedNoteUseCase
import com.danny.note.domain.usecase.note.SaveNoteUseCase

class NoteViewModelFactory(
    private val getSavedColorUseCase: GetSavedColorUseCase,
    private val saveColorUseCase: SaveColorUseCase,
    private val deleteColorUseCase: DeleteColorUseCase,
    private val getSavedNoteUseCase: GetSavedNoteUseCase,
    private val saveNoteUseCase: SaveNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NoteViewModel(
            getSavedColorUseCase,
            saveColorUseCase,
            deleteColorUseCase,
            getSavedNoteUseCase,
            saveNoteUseCase,
            deleteNoteUseCase
        ) as T
    }

}