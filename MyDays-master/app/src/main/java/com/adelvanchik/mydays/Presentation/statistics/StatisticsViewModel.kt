package com.adelvanchik.mydays.Presentation.statistics

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.adelvanchik.mydays.Data.parameters.ParametersRepositoryImpl
import com.adelvanchik.mydays.Domain.entity.Parameters
import com.adelvanchik.mydays.Domain.usecases.workWithParametersDB.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class StatisticsViewModel(application: Application): AndroidViewModel(application){

    private val repository = ParametersRepositoryImpl(application)
    private val getParametersListUseCase = GetParametersListUseCase(repository)
    private val getCurrentParametersUseCase = GetCurrentParametersUseCase(repository)
    private val deleteParametersUseCase = DeleteParametersUseCase(repository)
    private val addParametersUseCase = AddParametersUseCase(repository)

    val parametersList = getParametersListUseCase()

    private val _parametersLiveData = MutableLiveData<Parameters>()
    val parametersLiveData: LiveData<Parameters>
        get() = _parametersLiveData

    private val _currentData = MutableLiveData<Int>()
    val currentData: LiveData<Int>
        get() = _currentData

    fun initVM() {
        getCurrentParameters()
        initCurrentData()
    }

    fun deleteParameters(data: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteParametersUseCase(data)
        }
    }

    fun updateParameters(parameters: Parameters) {
        viewModelScope.launch(Dispatchers.IO) {
            addParametersUseCase(parameters)
        }
    }

    private fun getCurrentParameters() {
        viewModelScope.launch(Dispatchers.IO) {
            _parametersLiveData.postValue(getCurrentParametersUseCase())
        }
    }

    private fun initCurrentData() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        // месяц исчисляется с нуля, добавляем единицу
        val month = c.get(Calendar.MONTH) + ADD_ONE_FOR_MONTH
        val day = c.get(Calendar.DAY_OF_MONTH)

        // создаем id Дня
        // для 22 01 2022 id будет равен 2022/22/01 (год/месяц/число) без слэша
        val id = year * 10000 + month * 100 + day
        _currentData.value = id
    }
    companion object {
        private const val ADD_ONE_FOR_MONTH = 1
    }

}