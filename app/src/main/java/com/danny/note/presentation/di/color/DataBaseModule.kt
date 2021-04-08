package com.danny.note.presentation.di.color

import android.app.Application
import androidx.room.Room
import com.danny.note.data.db.color.ColorDAO
import com.danny.note.data.db.color.ColorDatabase
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
    fun provideColorDatabase(app : Application) : ColorDatabase {
        return Room.databaseBuilder(app, ColorDatabase::class.java, "color_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideColorDao(colorDatabase: ColorDatabase) : ColorDAO {
        return colorDatabase.getColorDAO()
    }
}