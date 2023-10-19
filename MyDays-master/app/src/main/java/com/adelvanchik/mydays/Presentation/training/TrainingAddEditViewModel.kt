package com.adelvanchik.mydays.Presentation.training

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.adelvanchik.mydays.Data.training.TrainingDayRepositoryImpl
import com.adelvanchik.mydays.Domain.entity.Training
import com.adelvanchik.mydays.Domain.usecases.workWithTrainingDB.AddTrainingDayUseCase
import com.adelvanchik.mydays.Domain.usecases.workWithTrainingDB.DeleteTrainingDayUseCase
import com.adelvanchik.mydays.Domain.usecases.workWithTrainingDB.GetCurrentTrainingDayUseCase
import com.adelvanchik.mydays.Domain.usecases.workWithTrainingDB.GetTrainingDayUseCase
import com.adelvanchik.mydays.Presentation.chooseData.ChooseDataViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrainingAddEditViewModel(application: Application): AndroidViewModel(application) {
    private val repository = TrainingDayRepositoryImpl(application)
    private val addTrainingDayUseCase = AddTrainingDayUseCase(repository)
    private val getCurrentTrainingDayUseCase = GetCurrentTrainingDayUseCase(repository)
    private val getTrainingDayUseCase = GetTrainingDayUseCase(repository)
    private val deleteTrainingDayUseCase = DeleteTrainingDayUseCase(repository)

    private val _dataLiveData = MutableLiveData<Training>()
    val dataLiveData: LiveData<Training>
    get() = _dataLiveData

    private val _dataStringLiveData = MutableLiveData<String>()
    val dataStringLiveData: LiveData<String>
        get() = _dataStringLiveData

    fun editTraining(training: Training) {
        viewModelScope.launch(Dispatchers.IO) {
            addTrainingDayUseCase(training)
        }
    }

    fun deleteTrainingDay(training: Training) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTrainingDayUseCase(training)
        }
    }


    fun initTrainingWithCurrentData() {
        viewModelScope.launch(Dispatchers.IO) {
            val training = getCurrentTrainingDayUseCase()
            _dataLiveData.postValue(training)
            createFormatData(training)
        }
    }

    fun initTrainingWithData(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val training = getTrainingDayUseCase(id)
            _dataLiveData.postValue(training)
            createFormatData(training)
        }
    }

    fun createFormatData(training: Training) {
        val day = training.day
        val month = training.month
        val year = training.year
        val monthFormat = month.toString().padStart(COUNT_DIGIT, '0')
        val dataStringFormat = "$day.$monthFormat.$year"
        _dataStringLiveData.postValue(dataStringFormat)
    }

    companion object {
        private const val ADD_ONE_FOR_MONTH: Int = 1
        private const val COUNT_DIGIT = 2
    }
}