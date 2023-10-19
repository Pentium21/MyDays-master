package com.adelvanchik.mydays.Presentation.thoughts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.adelvanchik.mydays.Data.day.DayRepositoryImpl
import com.adelvanchik.mydays.Domain.entity.Thoughts
import com.adelvanchik.mydays.Domain.usecases.GetThoughtsUseCase.GetListOfThoughtsUseCase
import com.adelvanchik.mydays.Domain.usecases.GetThoughtsUseCase.UpdateThoughtsForDayUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ThoughtsViewModel(application: Application): AndroidViewModel(application){

    private val dayRepositoryImpl = DayRepositoryImpl(application)
    private val getListOfThoughtsUseCase = GetListOfThoughtsUseCase(dayRepositoryImpl)
    private val updateThoughtsForDayUseCase = UpdateThoughtsForDayUseCase(dayRepositoryImpl)
    val listThoughts = getListOfThoughtsUseCase()

    fun editThoughts(dayId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            updateThoughtsForDayUseCase(dayId, Thoughts.DEFAULT_STRING_VALUE)
        }
    }

}