package com.danny.note.data.repository.dataSource

import com.danny.note.data.model.Color
import kotlinx.coroutines.flow.Flow

interface ColorDataSource {
    suspend fun saveColor(color : Color)
    suspend fun deleteColor(color : Color)
    fun getAllColors() : Flow<List<Color>>
}