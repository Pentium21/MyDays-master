package com.adelvanchik.mydays.Domain.usecases.filters

import androidx.lifecycle.MutableLiveData
import com.adelvanchik.mydays.Domain.repositiry.DayFilterDataRepository

class NotifyFilterDataUseCase(val dayFilterDataRepository: DayFilterDataRepository) {
    operator fun invoke(): MutableLiveData<Unit> {
        return dayFilterDataRepository.notifyDataFilter()
    }
}