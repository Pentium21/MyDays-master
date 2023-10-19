package com.adelvanchik.mydays.Domain.usecases.workWithTrainingDB

import com.adelvanchik.mydays.Domain.entity.Training
import com.adelvanchik.mydays.Domain.repositiry.TrainingRepository

class GetTrainingDayUseCase(private val trainingRepository: TrainingRepository) {
    suspend operator fun invoke(id: Int): Training {
        return trainingRepository.getTrainingDay(id)
    }
}