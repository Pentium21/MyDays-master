package com.adelvanchik.mydays.Data.training

import com.adelvanchik.mydays.Data.day.DayDBModel
import com.adelvanchik.mydays.Domain.entity.Day
import com.adelvanchik.mydays.Domain.entity.Training

class TrainingMapper {

    fun entityTrainingToTrainingDBModel(myTrainingDay: Training): TrainingDBModel = TrainingDBModel(
        data = myTrainingDay.data,
        day = myTrainingDay.day,
        month = myTrainingDay.month,
        year = myTrainingDay.year,
        training = myTrainingDay.training,
    )

    fun entityTrainingDBModelToTraining(myTrainingDay: TrainingDBModel): Training = Training(
        data = myTrainingDay.data,
        day = myTrainingDay.day,
        month = myTrainingDay.month,
        year = myTrainingDay.year,
        training = myTrainingDay.training,
    )

    fun listTrainingDBModelToTraining(list: List<TrainingDBModel?>): List<Training> = list
        .filterNotNull().map { entityTrainingDBModelToTraining(it)
    }


}