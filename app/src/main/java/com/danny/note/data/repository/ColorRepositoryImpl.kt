package com.danny.note.data.repository

import com.danny.note.data.model.Color
import com.danny.note.data.repository.dataSource.ColorDataSource
import com.danny.note.domain.repository.ColorRepository
import kotlinx.coroutines.flow.Flow

class ColorRepositoryImpl(
    private val colorDataSource: ColorDataSource
) : ColorRepository {
    override fun getColors(): Flow<List<Color>> = colorDataSource.getAllColors()

    override suspend fun saveColor(color: Color) = colorDataSource.saveColor(color)

    override suspend fun deleteColor(color: Color) = colorDataSource.deleteColor(color)
}