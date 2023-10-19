package com.adelvanchik.mydays.Domain.usecases.filters

import androidx.lifecycle.MutableLiveData
import com.adelvanchik.mydays.Domain.repositiry.DayFilterEffectivenessRepository

class NotifyFilterEffectivenessUseCase(
    val dayFilterEffectivenessRepository: DayFilterEffectivenessRepository) {

    operator fun invoke(): MutableLiveData<Unit> {
        return dayFilterEffectivenessRepository.notifyEffectivenessFilter()
    }
}