package com.adelvanchik.mydays.Domain.usecases.GetThoughtsUseCase

import com.adelvanchik.mydays.Domain.repositiry.DayRepository
import com.adelvanchik.mydays.Domain.entity.Day

class GetDayUseCase(private val dayRepository: DayRepository) {
    suspend operator fun invoke(dayId: Int): Day {
        return dayRepository.getDay(dayId)
    }
}
