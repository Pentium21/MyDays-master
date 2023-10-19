package com.adelvanchik.mydays.Presentation.thoughts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.adelvanchik.mydays.Data.day.DayRepositoryImpl
import com.adelvanchik.mydays.Domain.entity.Thoughts
import com.adelvanchik.mydays.Domain.usecases.GetThoughtsUseCase.GetThoughtsDay
import com.adelvanchik.mydays.Domain.usecases.GetThoughtsUseCase.UpdateThoughtsForDayUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditThoughtsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DayRepositoryImpl(application)
    private val updateThoughtsForDayUseCase = UpdateThoughtsForDayUseCase(repository)
    private val getThoughtsDay = GetThoughtsDay(repository)

    private val _dataLiveData = MutableLiveData<Thoughts>()
    val dataLiveData: LiveData<Thoughts>
        get() = _dataLiveData

    private val _dataStringLiveData = MutableLiveData<String>()
    val dataStringLiveData: LiveData<String>
        get() = _dataStringLiveData

    fun editThoughts(dayId: Int, value: String = Thoughts.DEFAULT_STRING_VALUE) {
        viewModelScope.launch(Dispatchers.IO) {
            updateThoughtsForDayUseCase(dayId, value)
        }
    }

    fun initThoughts(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val thoughts = getThoughtsDay(id)
            _dataLiveData.postValue(thoughts)
            createFormatData(thoughts)
        }
    }

    private fun createFormatData(thoughts: Thoughts) {
        val day = thoughts.day
        val month = thoughts.month
        val year = thoughts.year
        val monthFormat = month.toString().padStart(COUNT_DIGIT, '0')
        val dataStringFormat = "$day.$monthFormat.$year"
        _dataStringLiveData.postValue(dataStringFormat)
    }

    companion object {
        private const val ADD_ONE_FOR_MONTH: Int = 1
        private const val COUNT_DIGIT = 2
    }
}