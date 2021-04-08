package com.danny.note.data.repository.dataSourceImpl

import com.danny.note.data.db.color.ColorDAO
import com.danny.note.data.model.Color
import com.danny.note.data.repository.dataSource.ColorDataSource
import kotlinx.coroutines.flow.Flow

class ColorDataSourceImpl(private val colorDAO: ColorDAO) : ColorDataSource{
    override suspend fun saveColor(color: Color) {
        colorDAO.insert(color)
    }

    override suspend fun deleteColor(color: Color) {
        colorDAO.delete(color)
    }

    override fun getAllColors(): Flow<List<Color>> = colorDAO.getAllColors()
}