package com.danny.note.domain.usecase.color

import com.danny.note.data.model.Color
import com.danny.note.domain.repository.ColorRepository
import kotlinx.coroutines.flow.Flow

class GetSavedColorUseCase(private val colorRepository: ColorRepository) {
    fun execute() : Flow<List<Color>> = colorRepository.getColors()
}