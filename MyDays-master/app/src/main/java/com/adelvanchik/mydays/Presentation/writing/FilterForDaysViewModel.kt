package com.adelvanchik.mydays.Presentation.writing

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.adelvanchik.mydays.Data.day.DayRepositoryImpl
import com.adelvanchik.mydays.Data.filter.DayFilterDataImpl
import com.adelvanchik.mydays.Data.filter.DayFilterEffectivenessImpl
import com.adelvanchik.mydays.Domain.entity.FilterData
import com.adelvanchik.mydays.Domain.entity.FilterEffectiveness
import com.adelvanchik.mydays.Domain.usecases.filters.GetDayFilterDataUseCase
import com.adelvanchik.mydays.Domain.usecases.filters.GetDayFilterEffectivenessUseCase
import com.adelvanchik.mydays.Domain.usecases.filters.SaveDayFilterDataUseCase
import com.adelvanchik.mydays.Domain.usecases.filters.SaveDayFilterEffectivenessUseCase
import com.adelvanchik.mydays.Domain.usecases.GetThoughtsUseCase.GetMinYearFromTableDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FilterForDaysViewModel(application: Application) : AndroidViewModel(application) {

    private val dayFilterDataImpl = DayFilterDataImpl(application)
    private val dayFilterEffectivenessImpl = DayFilterEffectivenessImpl(application)
    private val dayRepositoryImpl = DayRepositoryImpl(application)

    private val getDayFilterDataUseCase = GetDayFilterDataUseCase(dayFilterDataImpl)
    private val saveDayFilterDataUseCase = SaveDayFilterDataUseCase(dayFilterDataImpl)
    private val saveDayFilterEffectivenessUseCase = SaveDayFilterEffectivenessUseCase(
        dayFilterEffectivenessImpl)
    private val getDayFilterEffectivenessUseCase = GetDayFilterEffectivenessUseCase(
        dayFilterEffectivenessImpl)
    private val getMinYearFromTableDay = GetMinYearFromTableDay(dayRepositoryImpl)

    private val _filterDataLiveData = MutableLiveData<FilterData>()
    val filterDataLiveData: LiveData<FilterData>
        get() = _filterDataLiveData

    private val _filterEffectivenessLiveData = MutableLiveData<FilterEffectiveness>()
    val filterEffectivenessLiveData: LiveData<FilterEffectiveness>
        get() = _filterEffectivenessLiveData


    private val _minYear = MutableLiveData<Short>()
    val minYear: LiveData<Short>
        get() = _minYear

    fun initValues() {
        getDayFilterData()
        getDayFilterEffectiveness()
        getMinYearFromTable()
    }

    fun getMinYearFromTable() {
        viewModelScope.launch(Dispatchers.Default) {
            _minYear.postValue(getMinYearFromTableDay() ?: DEFAULT_MIN_VALUE)
        }
    }

    fun saveDayFilterData(filterData: FilterData) {
        viewModelScope.launch(Dispatchers.Default) {
            saveDayFilterDataUseCase(filterData)
        }
    }

    fun saveDayFilterEffectiveness(filterEffectiveness: FilterEffectiveness) {
        viewModelScope.launch(Dispatchers.Default) {
            saveDayFilterEffectivenessUseCase(filterEffectiveness)
        }
    }

    private fun getDayFilterData() {
        runBlocking {
            var filterData = FilterData()
            val job = viewModelScope.launch(Dispatchers.IO) {
                filterData = getDayFilterDataUseCase()
            }
            job.join()
            _filterDataLiveData.value = filterData
        }
    }

    private fun getDayFilterEffectiveness() {
        runBlocking {
            var filterEffectiveness = FilterEffectiveness()
            val job = viewModelScope.launch(Dispatchers.IO) {
                filterEffectiveness = getDayFilterEffectivenessUseCase()
            }
            job.join()
            _filterEffectivenessLiveData.value = filterEffectiveness
        }
    }

    companion object {
        const val DEFAULT_MIN_VALUE: Short = -1
    }

}