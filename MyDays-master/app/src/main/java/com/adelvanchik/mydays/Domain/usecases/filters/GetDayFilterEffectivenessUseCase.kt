package com.adelvanchik.mydays.Domain.usecases.filters

import com.adelvanchik.mydays.Domain.entity.FilterEffectiveness
import com.adelvanchik.mydays.Domain.repositiry.DayFilterEffectivenessRepository

class GetDayFilterEffectivenessUseCase(
    val dayFilterEffectivenessRepository: DayFilterEffectivenessRepository) {
    suspend operator fun invoke(): FilterEffectiveness {
        return dayFilterEffectivenessRepository.getDayFilterEffectiveness()
    }
}