package com.danny.note.domain.repository

import com.danny.note.data.model.Color
import kotlinx.coroutines.flow.Flow

interface ColorRepository {
    fun getColors() : Flow<List<Color>>
    suspend fun saveColor(color : Color)
    suspend fun deleteColor(color : Color)
}