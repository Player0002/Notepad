package com.danny.note.presentation.di.note

import com.danny.note.data.repository.ColorRepositoryImpl
import com.danny.note.data.repository.NoteRepositoryImpl
import com.danny.note.data.repository.dataSource.ColorDataSource
import com.danny.note.data.repository.dataSource.NoteDataSource
import com.danny.note.domain.repository.ColorRepository
import com.danny.note.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideNoteRepository(noteDataSource: NoteDataSource) : NoteRepository{
        return NoteRepositoryImpl(noteDataSource)
    }
}