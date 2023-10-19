package com.adelvanchik.mydays.Domain.usecases.filters

import com.adelvanchik.mydays.Domain.entity.FilterData
import com.adelvanchik.mydays.Domain.repositiry.DayFilterDataRepository

class SaveDayFilterDataUseCase(val dayFilterDataRepository: DayFilterDataRepository) {
    suspend operator fun invoke(filterData: FilterData) {
        dayFilterDataRepository.saveDayFilterData(filterData)
    }
}