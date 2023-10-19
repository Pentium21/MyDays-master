package com.adelvanchik.mydays.Domain.repositiry

import androidx.lifecycle.MutableLiveData
import com.adelvanchik.mydays.Domain.entity.FilterEffectiveness

interface DayFilterEffectivenessRepository {

    suspend fun getDayFilterEffectiveness(): FilterEffectiveness

    suspend fun saveDayFilterEffectiveness(filterEffectiveness: FilterEffectiveness)

    fun notifyEffectivenessFilter(): MutableLiveData<Unit>
}