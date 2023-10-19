package com.adelvanchik.mydays.Presentation.training

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.adelvanchik.mydays.Data.training.TrainingDayRepositoryImpl
import com.adelvanchik.mydays.Domain.entity.Training
import com.adelvanchik.mydays.Domain.usecases.workWithTrainingDB.DeleteTrainingDayUseCase
import com.adelvanchik.mydays.Domain.usecases.workWithTrainingDB.GetListOfTrainingUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrainingViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TrainingDayRepositoryImpl(application)
    private val getListOfTrainingUseCase = GetListOfTrainingUseCase(repository)
    private val deleteTrainingDayUseCase = DeleteTrainingDayUseCase(repository)

    val trainingList = getListOfTrainingUseCase()

    fun deleteTrainingDay(training: Training) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTrainingDayUseCase(training)
        }
    }

}