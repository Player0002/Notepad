package com.danny.note.presentation.di.note

import com.danny.note.domain.repository.NoteRepository
import com.danny.note.domain.usecase.note.DeleteNoteUseCase
import com.danny.note.domain.usecase.note.GetSavedNoteUseCase
import com.danny.note.domain.usecase.note.SaveNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    fun provideSaveNoteUseCase(colorRepository: NoteRepository) : SaveNoteUseCase {
        return SaveNoteUseCase(colorRepository)
    }

    @Provides
    @Singleton
    fun provideGetSavedNoteUseCase(colorRepository: NoteRepository) : GetSavedNoteUseCase {
        return GetSavedNoteUseCase(colorRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteNoteUseCase(colorRepository: NoteRepository) : DeleteNoteUseCase {
        return DeleteNoteUseCase(colorRepository)
    }
}