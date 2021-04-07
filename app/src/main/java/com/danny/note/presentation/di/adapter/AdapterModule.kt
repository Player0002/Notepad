package com.danny.note.presentation.di.adapter

import com.danny.note.presentation.adapter.FilterAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {
    @Provides
    @Singleton
    fun provideFilterAdapter() : FilterAdapter{
        return FilterAdapter()
    }
}