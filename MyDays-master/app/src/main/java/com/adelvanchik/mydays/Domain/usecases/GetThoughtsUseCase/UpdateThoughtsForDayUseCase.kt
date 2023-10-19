package com.adelvanchik.mydays.Domain.usecases.GetThoughtsUseCase

import com.adelvanchik.mydays.Domain.repositiry.DayRepository

class UpdateThoughtsForDayUseCase(private val dayRepository: DayRepository) {
    suspend operator fun invoke(dayId: Int, value: String) {
        dayRepository.updateThoughtsForDay(dayId, value)
    }
}