package com.adelvanchik.mydays.Domain.usecases.GetThoughtsUseCase

import androidx.lifecycle.LiveData
import com.adelvanchik.mydays.Domain.entity.Day
import com.adelvanchik.mydays.Domain.entity.FilterData
import com.adelvanchik.mydays.Domain.entity.MinMaxEffectiveness
import com.adelvanchik.mydays.Domain.repositiry.DayRepository

class GetListOfDaysUseCase(private val dayRepository: DayRepository) {
    operator fun invoke(minMaxEffectiveness: MinMaxEffectiveness = MinMaxEffectiveness(),
                        filterData: FilterData = FilterData(),
    ): LiveData<List<Day>> {
        return dayRepository.getListOfDays(minMaxEffectiveness,filterData)
    }
}