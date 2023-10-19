package com.adelvanchik.mydays.Domain.repositiry

import androidx.lifecycle.LiveData
import com.adelvanchik.mydays.Domain.entity.Training

interface TrainingRepository {

    suspend fun getTrainingDay(trainingDayId: Int): Training

    suspend fun getCurrentTrainingDay(): Training

    suspend fun addTrainingDay(training: Training)

    suspend fun deleteTrainingDay(training: Training)

    fun getListOfTrainingDay(): LiveData<List<Training>>
}