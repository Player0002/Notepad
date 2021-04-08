package com.danny.note.presentation.di.color

import com.danny.note.domain.repository.ColorRepository
import com.danny.note.domain.usecase.color.DeleteColorUseCase
import com.danny.note.domain.usecase.color.GetSavedColorUseCase
import com.danny.note.domain.usecase.color.SaveColorUseCase
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
    fun provideSaveColorUseCase(colorRepository: ColorRepository) : SaveColorUseCase {
        return SaveColorUseCase(colorRepository)
    }

    @Provides
    @Singleton
    fun provideGetSavedColorUseCase(colorRepository: ColorRepository) : GetSavedColorUseCase {
        return GetSavedColorUseCase(colorRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteColorUseCase(colorRepository: ColorRepository) : DeleteColorUseCase {
        return DeleteColorUseCase(colorRepository)
    }
}