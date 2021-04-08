package com.danny.note.domain.usecase.color

import com.danny.note.data.model.Color
import com.danny.note.domain.repository.ColorRepository

class DeleteColorUseCase (private val colorRepository: ColorRepository) {
    suspend fun execute(color : Color) = colorRepository.deleteColor(color)
}