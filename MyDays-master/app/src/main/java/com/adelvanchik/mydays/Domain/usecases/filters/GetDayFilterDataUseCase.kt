package com.adelvanchik.mydays.Domain.usecases.filters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adelvanchik.mydays.Domain.entity.FilterData
import com.adelvanchik.mydays.Domain.repositiry.DayFilterDataRepository

class GetDayFilterDataUseCase(val dayFilterDataRepository: DayFilterDataRepository) {
    suspend operator fun invoke(): FilterData {
        return dayFilterDataRepository.getDayFilterData()
    }
}