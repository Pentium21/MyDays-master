package com.adelvanchik.mydays.Data.day

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.adelvanchik.mydays.Data.AppDatabase
import com.adelvanchik.mydays.Domain.entity.Day
import com.adelvanchik.mydays.Domain.entity.FilterData
import com.adelvanchik.mydays.Domain.entity.MinMaxEffectiveness
import com.adelvanchik.mydays.Domain.entity.Thoughts
import com.adelvanchik.mydays.Domain.repositiry.DayRepository

class DayRepositoryImpl(
    application: Application,
) : DayRepository {

    private val dayMapper = DayMapper()
    private val daoList = AppDatabase.getInstance(application = application).DayListDao()

    override suspend fun addDay(day: Day) {
        daoList.addDay(dayMapper.entityDayToDayDBModel(day))
    }

    override suspend fun editDay(day: Day) {
        daoList.addDay(dayMapper.entityDayToDayDBModel(day))
    }

    override suspend fun deleteDay(day: Day) {
        daoList.deleteDay(day.data)
    }

    override suspend fun getDay(dayId: Int): Day {
        return dayMapper.entityDayDBModelToDay(daoList.getDay(dayId))
    }

    override suspend fun checkHaveDayInTable(dayId: Int): Boolean {
        return daoList.isHaveDayInTable(dayId)
    }

    override suspend fun getMinYearFromTableDay(): Short? {
        return daoList.getMinYearInTableDay()
    }

    override suspend fun updateThoughtsForDay(dayId: Int, value: String) {
        daoList.updateThoughtsForDay(dayId, value)
    }

    override suspend fun getThoughtsDay(id: Int): Thoughts {
        return daoList.getThoughtsDay(id)
    }

    override fun getListOfDays(
        minMaxEffectiveness: MinMaxEffectiveness,
        filterData: FilterData,
    ): LiveData<List<Day>> {
        // каждый день имеет Data (id) типа ГГГГММДД, будем переводить минимальную и максимальную
        // даты в данный формат
        // к месяцу будем прибавлять 1, поскольку месяц в фильтре сохраняется начиная с 0
        val minData: Int = filterData.yearStart * 10_000 + (filterData.monthStart+1) * 100
        // Чтобы пограничный месяц тоже включался, то добавим 31
        val maxData: Int = filterData.yearEnd * 10_000 + (filterData.monthEnd+1) * 100 + 31
        Log.d("DayRepositoryImpl", "Начало даты: $minData")
        Log.d("DayRepositoryImpl", "Конец даты: $maxData")
        return Transformations.map(
            daoList.getListOfDays(
                minMaxEffectiveness.min, minMaxEffectiveness.max, minData, maxData)
        ) {
            dayMapper.listDayDBModelToDay(it).sortedByDescending { it.data }
        }
    }

    override fun getListOfThoughts(): LiveData<List<Thoughts>> {
        return daoList.getListOfThoughts(Thoughts.DEFAULT_STRING_VALUE)
    }
}