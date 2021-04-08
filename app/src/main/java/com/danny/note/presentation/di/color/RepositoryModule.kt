package com.danny.note.presentation.di.color

import com.danny.note.data.repository.ColorRepositoryImpl
import com.danny.note.data.repository.dataSource.ColorDataSource
import com.danny.note.domain.repository.ColorRepository
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
    fun provideColorRepository(colorDataSource: ColorDataSource) : ColorRepository{
        return ColorRepositoryImpl(colorDataSource)
    }
}