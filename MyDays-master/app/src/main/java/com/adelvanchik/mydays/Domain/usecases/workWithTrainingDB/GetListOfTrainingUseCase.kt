package com.adelvanchik.mydays.Domain.usecases.workWithTrainingDB

import androidx.lifecycle.LiveData
import com.adelvanchik.mydays.Domain.entity.Training
import com.adelvanchik.mydays.Domain.repositiry.TrainingRepository

class GetListOfTrainingUseCase(private val trainingRepository: TrainingRepository) {
    operator fun invoke(): LiveData<List<Training>> {
        return trainingRepository.getListOfTrainingDay()
    }
}