package com.adelvanchik.mydays.Domain.repositiry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adelvanchik.mydays.Domain.entity.FilterData

interface DayFilterDataRepository {

    suspend fun getDayFilterData(): FilterData

    suspend fun saveDayFilterData(filterData: FilterData)

    fun notifyDataFilter(): MutableLiveData<Unit>
}