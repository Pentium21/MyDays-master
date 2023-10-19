package com.adelvanchik.mydays.Domain.repositiry

import androidx.lifecycle.LiveData
import com.adelvanchik.mydays.Domain.entity.Day
import com.adelvanchik.mydays.Domain.entity.FilterData
import com.adelvanchik.mydays.Domain.entity.MinMaxEffectiveness
import com.adelvanchik.mydays.Domain.entity.Thoughts

interface DayRepository {

    suspend fun addDay(day: Day)

    suspend fun editDay(day: Day)

    suspend fun deleteDay(day: Day)

    suspend fun getDay(dayId: Int): Day

    suspend fun checkHaveDayInTable(dayId: Int): Boolean

    suspend fun getMinYearFromTableDay(): Short?

    suspend fun updateThoughtsForDay(dayId: Int, value: String)

    suspend fun getThoughtsDay(id: Int): Thoughts

    fun getListOfDays(
        minMaxEffectiveness: MinMaxEffectiveness,
        filterData: FilterData,
    ): LiveData<List<Day>>

    fun getListOfThoughts(): LiveData<List<Thoughts>>
}