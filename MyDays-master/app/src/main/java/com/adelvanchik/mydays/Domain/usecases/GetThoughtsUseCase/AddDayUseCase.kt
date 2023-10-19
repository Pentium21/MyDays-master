package com.adelvanchik.mydays.Domain.usecases.GetThoughtsUseCase

import com.adelvanchik.mydays.Domain.repositiry.DayRepository
import com.adelvanchik.mydays.Domain.entity.Day

class AddDayUseCase(private val dayRepository: DayRepository) {

    suspend operator fun invoke(day: Day) {
        dayRepository.addDay(day)
    }
}