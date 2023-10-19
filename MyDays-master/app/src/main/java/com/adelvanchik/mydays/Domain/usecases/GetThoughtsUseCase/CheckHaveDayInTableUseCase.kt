package com.adelvanchik.mydays.Domain.usecases.GetThoughtsUseCase

import com.adelvanchik.mydays.Domain.repositiry.DayRepository

class CheckHaveDayInTableUseCase(private val dayRepository: DayRepository) {
    suspend operator fun invoke(dayId: Int): Boolean {
        return dayRepository.checkHaveDayInTable(dayId)
    }
}