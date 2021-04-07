package com.danny.note.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.danny.note.domain.usecase.color.DeleteColorUseCase
import com.danny.note.domain.usecase.color.GetSavedColorUseCase
import com.danny.note.domain.usecase.color.SaveColorUseCase

class NoteViewModelFactory(
    private val getSavedColorUseCase: GetSavedColorUseCase,
    private val saveColorUseCase: SaveColorUseCase,
    private val deleteColorUseCase: DeleteColorUseCase
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NoteViewModel(getSavedColorUseCase, saveColorUseCase, deleteColorUseCase) as T
    }

}