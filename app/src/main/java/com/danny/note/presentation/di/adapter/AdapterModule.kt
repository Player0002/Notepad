package com.danny.note.presentation.di.adapter

import com.danny.note.presentation.adapter.FilterAdapter
import com.danny.note.presentation.adapter.PreviewAdapter
import com.danny.note.presentation.adapter.SearchAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {
    @Provides
    fun provideFilterAdapter() : FilterAdapter{
        return FilterAdapter()
    }
    @Provides
    @Singleton
    fun provideSearchAdapter() : SearchAdapter {
        return SearchAdapter()
    }
    @Provides
    @Singleton
    fun providePreviewAdapter() : PreviewAdapter {
        return PreviewAdapter()
    }
}