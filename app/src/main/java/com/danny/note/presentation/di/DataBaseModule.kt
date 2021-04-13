package com.danny.note.presentation.di

import android.app.Application
import androidx.room.Room
import com.danny.note.data.db.color.ColorDAO
import com.danny.note.data.db.NoteDatabase
import com.danny.note.data.db.note.NoteDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Singleton
    @Provides
    fun provideNoteDatabase(app : Application) : NoteDatabase {
        return Room.databaseBuilder(app, NoteDatabase::class.java, "note_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideColorDao(colorDatabase: NoteDatabase) : ColorDAO {
        return colorDatabase.getColorDAO()
    }

    @Singleton
    @Provides
    fun provideNoteDao(noteDatabase: NoteDatabase) : NoteDAO {
        return noteDatabase.getNoteDAO()
    }
}