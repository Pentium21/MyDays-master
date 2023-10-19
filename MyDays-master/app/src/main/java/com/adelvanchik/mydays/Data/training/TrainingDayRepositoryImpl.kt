package com.adelvanchik.mydays.Data.training

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.adelvanchik.mydays.Data.AppDatabase
import com.adelvanchik.mydays.Data.day.DayMapper
import com.adelvanchik.mydays.Domain.entity.Training
import com.adelvanchik.mydays.Domain.repositiry.TrainingRepository
import com.adelvanchik.mydays.Presentation.training.TrainingAddEditViewModel
import java.util.*

class TrainingDayRepositoryImpl(val application: Application) : TrainingRepository {

    private val trainingMapper = TrainingMapper()
    private val daoList = AppDatabase.getInstance(application = application).TrainingListDao()

    override suspend fun getTrainingDay(trainingDayId: Int): Training {
        val training = daoList.getTrainingDay(trainingDayId)
        return trainingMapper.entityTrainingDBModelToTraining(training
            ?: throw RuntimeException("Not found training day with id: $trainingDayId"))
    }

    override suspend fun getCurrentTrainingDay(): Training {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        // месяц исчисляется с нуля, добавляем единицу
        val month = c.get(Calendar.MONTH) + ADD_ONE_FOR_MONTH
        val day = c.get(Calendar.DAY_OF_MONTH)

        // создаем id Дня
        // для 22 01 2022 id будет равен 2022/22/01 (год/месяц/число) без слэша
        val id = year * 10000 + month * 100 + day
        val training = daoList.getTrainingDay(id)
        if (training!=null) return trainingMapper.entityTrainingDBModelToTraining(training)
        return Training(
            data = id,
            day = day.toShort(),
            month = month.toShort(),
            year = year.toShort()
        )
    }

    override suspend fun addTrainingDay(training: Training) {
        daoList.addTrainingDay(trainingMapper.entityTrainingToTrainingDBModel(training))
    }

    override suspend fun deleteTrainingDay(training: Training) {
        daoList.deleteTrainingDay(training.data)
    }

    override fun getListOfTrainingDay(): LiveData<List<Training>> {
        return Transformations.map(
            daoList.getListOfTrainingDay()
        ) {
            trainingMapper.listTrainingDBModelToTraining(it).sortedByDescending { it.data }
        }
    }

    companion object {
        private const val ADD_ONE_FOR_MONTH = 1
    }
}