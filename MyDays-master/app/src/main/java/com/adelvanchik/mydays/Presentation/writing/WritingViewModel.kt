package com.adelvanchik.mydays.Presentation.writing

import android.app.Application
import androidx.lifecycle.*
import com.adelvanchik.mydays.Data.day.DayRepositoryImpl
import com.adelvanchik.mydays.Data.filter.DayFilterDataImpl
import com.adelvanchik.mydays.Data.filter.DayFilterEffectivenessImpl
import com.adelvanchik.mydays.Domain.entity.Day
import com.adelvanchik.mydays.Domain.entity.FilterData
import com.adelvanchik.mydays.Domain.entity.FilterEffectiveness
import com.adelvanchik.mydays.Domain.entity.MinMaxEffectiveness
import com.adelvanchik.mydays.Domain.usecases.GetThoughtsUseCase.DeleteDayUseCase
import com.adelvanchik.mydays.Domain.usecases.filters.GetDayFilterDataUseCase
import com.adelvanchik.mydays.Domain.usecases.filters.GetDayFilterEffectivenessUseCase
import com.adelvanchik.mydays.Domain.usecases.filters.NotifyFilterDataUseCase
import com.adelvanchik.mydays.Domain.usecases.filters.NotifyFilterEffectivenessUseCase
import com.adelvanchik.mydays.Domain.usecases.GetThoughtsUseCase.GetListOfDaysUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class WritingViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DayRepositoryImpl(application = application)
    private val getListOfDays = GetListOfDaysUseCase(repository)
    private val deleteDayUseCase = DeleteDayUseCase(repository)

    private val dayFilterDataImpl = DayFilterDataImpl(application)
    private val getDayFilterDataUseCase = GetDayFilterDataUseCase(dayFilterDataImpl)
    private val notifyFilterDataUseCase = NotifyFilterDataUseCase(dayFilterDataImpl)
    val notifyFilterDataLiveData = notifyFilterDataUseCase()

    private val dayFilterEffectivenessImpl = DayFilterEffectivenessImpl(application)
    private val getDayFilterEffectivenessUseCase = GetDayFilterEffectivenessUseCase(
        dayFilterEffectivenessImpl)
    private val notifyFilterEffectivenessUseCase = NotifyFilterEffectivenessUseCase(
        dayFilterEffectivenessImpl)
    val notifyFilterEffectivenessLiveData = notifyFilterEffectivenessUseCase()

    private val _openFilterLiveData = MutableLiveData<Boolean>()
    fun getValueFromOpenFilterLiveData() = _openFilterLiveData.value

    fun changeConditionFilterFragment() {
        _openFilterLiveData.value = _openFilterLiveData.value == false
    }

    fun setFalseValueForConditionFilterFragment() {
        _openFilterLiveData.value = false
    }

    private val instanceFilterFragment = MutableLiveData<FilterForDaysFragment>()
    fun getInstanceFilterFragment() = instanceFilterFragment.value

    lateinit var dayListEffectiveness0: LiveData<List<Day>>

    lateinit var dayListEffectiveness1: LiveData<List<Day>>

    lateinit var dayListEffectiveness2: LiveData<List<Day>>

    lateinit var dayListEffectiveness3: LiveData<List<Day>>

    private val mediatorLiveData = MediatorLiveData<List<Day>>()

    init {
        instanceFilterFragment.value = FilterForDaysFragment.newInstance()
        setFalseValueForConditionFilterFragment()
        addFilterData()
    }

    private fun combineDayLists() {
        val list = mutableListOf<Day>()
        runBlocking {
            var filterEffectiveness = FilterEffectiveness()
            val job = viewModelScope.launch(Dispatchers.IO) {
                filterEffectiveness = getDayFilterEffectivenessUseCase()
            }
            job.join()
            if (filterEffectiveness.isIncludeEffectivenessEmpty) {
                list.addAll(dayListEffectiveness0.value.orEmpty())
            }
            if (filterEffectiveness.isIncludeEffectiveness1) {
                list.addAll(dayListEffectiveness1.value.orEmpty())
            }
            if (filterEffectiveness.isIncludeEffectiveness2) {
                list.addAll(dayListEffectiveness2.value.orEmpty())
            }
            if (filterEffectiveness.isIncludeEffectiveness3) {
                list.addAll(dayListEffectiveness3.value.orEmpty())
            }
            mediatorLiveData.value = list.sortedByDescending { it.data }
        }
    }

    val dayList: LiveData<List<Day>> = mediatorLiveData

    fun addFilterData() {
        var filterData = FilterData()
        runBlocking {
            val job = viewModelScope.launch(Dispatchers.IO) {
                filterData = getDayFilterDataUseCase()
            }
            job.join()
            dayListEffectiveness0 = getListOfDays(
                MinMaxEffectiveness(Day.DEFAULT_SHORT_VALUE, Day.DEFAULT_SHORT_VALUE), filterData)

            dayListEffectiveness1 = getListOfDays(
                MinMaxEffectiveness(Day.EFFECTIVENESS_1_START, Day.EFFECTIVENESS_1_END), filterData)

            dayListEffectiveness2 = getListOfDays(
                MinMaxEffectiveness(Day.EFFECTIVENESS_2_START, Day.EFFECTIVENESS_2_END), filterData)

            dayListEffectiveness3 = getListOfDays(
                MinMaxEffectiveness(Day.EFFECTIVENESS_3_START, Day.EFFECTIVENESS_3_END), filterData)
            mediatorAddSource()
        }
    }

    fun deleteDay(day: Day) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteDayUseCase(day)
        }
    }

    fun changeFilterData() {
        mediatorRemoveSource()
        addFilterData()
    }

    private fun mediatorRemoveSource() {
        with(mediatorLiveData) {
            removeSource(dayListEffectiveness0)
            removeSource(dayListEffectiveness1)
            removeSource(dayListEffectiveness2)
            removeSource(dayListEffectiveness3)
        }
    }

    private fun mediatorAddSource() {
        with(mediatorLiveData) {
            addSource(dayListEffectiveness0) { combineDayLists() }
            addSource(dayListEffectiveness1) { combineDayLists() }
            addSource(dayListEffectiveness2) { combineDayLists() }
            addSource(dayListEffectiveness3) { combineDayLists() }
        }
    }
}
