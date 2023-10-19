package com.adelvanchik.mydays.Presentation.chooseData

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.adelvanchik.mydays.Data.day.DayRepositoryImpl
import com.adelvanchik.mydays.Domain.entity.Day
import com.adelvanchik.mydays.Domain.usecases.GetThoughtsUseCase.CheckHaveDayInTableUseCase
import com.adelvanchik.mydays.Domain.usecases.GetThoughtsUseCase.GetDayUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChooseDataViewModel(application: Application) : AndroidViewModel(application) {

    private val _dataLiveData = MutableLiveData<String>()
    val dataLiveData: LiveData<String>
        get() = _dataLiveData

    private val _enabledNextButton = MutableLiveData<Unit>()
    val enabledNextButton: LiveData<Unit>
        get() = _enabledNextButton

    private val _messageAboutErrorDayChoice = MutableLiveData<Unit>()
    val messageAboutErrorDayChoice: LiveData<Unit>
        get() = _messageAboutErrorDayChoice

    private val _dayForNextFragment = MutableLiveData<Day>()
    val dayForNextFragment: LiveData<Day>
        get() = _dayForNextFragment

    private var dataId = DEFAULT_VALUE_FOR_DATA_ID

    private val repository = DayRepositoryImpl(application)

    private val getDayUseCase = GetDayUseCase(repository)
    private val checkHaveDayInTableUseCase = CheckHaveDayInTableUseCase(repository)


    fun checkData(
        year: Int, month: Int, day: Int,
        yearCurrent: Int, monthCurrent: Int, dayCurrent: Int,
    ) {
        if ((year < yearCurrent) ||
            (year == yearCurrent && month < monthCurrent) ||
            (year == yearCurrent && month == monthCurrent && day <= dayCurrent)
        ) {
            createFormatDate(day, month, year)
        } else _messageAboutErrorDayChoice.value = Unit
    }


    fun createFormatDate(day: Int, month: Int, year: Int) {
        val dayFormat = day.toString().padStart(COUNT_DIGIT, '0')
        // месяц исчисляется с нуля, добавляем единицу
        val myMonth = month + ADD_ONE_FOR_MONTH
        val monthFormat = myMonth.toString().padStart(COUNT_DIGIT, '0')
        _enabledNextButton.value = Unit
        val dataStringFormat = "$dayFormat.$monthFormat.$year"
        _dataLiveData.value = dataStringFormat

        // создаем id Дня
        // для 22 01 2022 id будет равен 2022/22/01 (год/месяц/число) без слэша
        dataId = year * 10000 + myMonth * 100 + day

        viewModelScope.launch(Dispatchers.IO) {
            val dayIsHaveInTable = checkHaveDayInTableUseCase(dataId)
            if (dayIsHaveInTable) {
                loadDayFromDataBase()
            } else {
                _dayForNextFragment.postValue(Day(
                    data = dataId,
                    day = day.toShort(),
                    month = myMonth.toShort(),
                    year = year.toShort(),
                    dataStringFormat = dataStringFormat,
                ))
            }
        }

    }

    fun loadDayFromDataBase() {
        if (dataId != DEFAULT_VALUE_FOR_DATA_ID) {
            viewModelScope.launch(Dispatchers.IO) {
                _dayForNextFragment.postValue(getDayUseCase(dataId))
            }
        }
    }

    companion object {
        private const val COUNT_DIGIT: Int = 2
        private const val ADD_ONE_FOR_MONTH: Int = 1
        private const val DEFAULT_VALUE_FOR_DATA_ID: Int = 0
    }

}