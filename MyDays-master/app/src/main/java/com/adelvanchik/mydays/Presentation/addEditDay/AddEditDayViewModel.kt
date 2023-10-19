package com.adelvanchik.mydays.Presentation.addEditDay

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.adelvanchik.mydays.Data.day.DayRepositoryImpl
import com.adelvanchik.mydays.Domain.entity.Day
import com.adelvanchik.mydays.Domain.usecases.GetThoughtsUseCase.AddDayUseCase
import com.adelvanchik.mydays.Domain.usecases.GetThoughtsUseCase.DeleteDayUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddEditDayViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DayRepositoryImpl(application)

    private val addDayUseCase = AddDayUseCase(repository)
    private val deleteDayUseCase = DeleteDayUseCase(repository)

    private val _myDay = MutableLiveData<Day>()
    val myDay: LiveData<Day>
        get() = _myDay

    fun initialDay(day: Day) {
        _myDay.value = day
    }

    fun addEditDay(day: Day) {
        _myDay.value = day
        viewModelScope.launch(Dispatchers.IO) {
            addDayUseCase(day)
        }
    }

    fun deleteDay(day: Day) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteDayUseCase(day)
        }
    }


}