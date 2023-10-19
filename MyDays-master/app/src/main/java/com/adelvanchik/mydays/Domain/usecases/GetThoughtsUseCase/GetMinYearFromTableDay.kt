package com.adelvanchik.mydays.Domain.usecases.GetThoughtsUseCase

import com.adelvanchik.mydays.Domain.repositiry.DayRepository

class GetMinYearFromTableDay(val dayRepository: DayRepository) {
    operator suspend fun invoke(): Short? {
        return dayRepository.getMinYearFromTableDay()
    }
}