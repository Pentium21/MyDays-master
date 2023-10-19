package com.adelvanchik.mydays.Domain.usecases.GetThoughtsUseCase

import androidx.lifecycle.LiveData
import com.adelvanchik.mydays.Domain.entity.Thoughts
import com.adelvanchik.mydays.Domain.repositiry.DayRepository

class GetListOfThoughtsUseCase(private val dayRepository: DayRepository) {
    operator fun invoke(): LiveData<List<Thoughts>> {
        return dayRepository.getListOfThoughts()
    }

}