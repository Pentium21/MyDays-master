package com.adelvanchik.mydays.Domain.usecases.GetThoughtsUseCase

import com.adelvanchik.mydays.Domain.entity.Thoughts
import com.adelvanchik.mydays.Domain.repositiry.DayRepository

class GetThoughtsDay(private val dayRepository: DayRepository) {
    suspend operator fun invoke(dayId: Int): Thoughts {
        return dayRepository.getThoughtsDay(dayId)
    }
}