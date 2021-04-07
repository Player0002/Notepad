package com.danny.note.presentation.di

import com.danny.note.domain.usecase.color.DeleteColorUseCase
import com.danny.note.domain.usecase.color.GetSavedColorUseCase
import com.danny.note.domain.usecase.color.SaveColorUseCase
import com.danny.note.presentation.viewModel.NoteViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Provides
    @Singleton
    fun provideNoteViewModelFactory(
        getSavedColorUseCase: GetSavedColorUseCase,
        savedColorUseCase: SaveColorUseCase,
        deleteColorUseCase: DeleteColorUseCase
    ): NoteViewModelFactory {
        return NoteViewModelFactory(getSavedColorUseCase, savedColorUseCase, deleteColorUseCase)
    }
}