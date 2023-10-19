package com.adelvanchik.mydays.Domain.usecases.workWithTrainingDB

import com.adelvanchik.mydays.Domain.entity.Training
import com.adelvanchik.mydays.Domain.repositiry.TrainingRepository

    class AddTrainingDayUseCase(private val trainingRepository: TrainingRepository) {
        suspend operator fun invoke(training: Training) {
            trainingRepository.addTrainingDay(training)
        }
}