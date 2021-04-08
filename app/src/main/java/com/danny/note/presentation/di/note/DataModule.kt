package com.danny.note.presentation.di.note

import com.danny.note.data.db.color.ColorDAO
import com.danny.note.data.db.note.NoteDAO
import com.danny.note.data.repository.dataSource.ColorDataSource
import com.danny.note.data.repository.dataSource.NoteDataSource
import com.danny.note.data.repository.dataSourceImpl.ColorDataSourceImpl
import com.danny.note.data.repository.dataSourceImpl.NoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideNoteDataSource(noteDAO: NoteDAO) : NoteDataSource {
        return NoteDataSourceImpl(noteDAO)
    }
}