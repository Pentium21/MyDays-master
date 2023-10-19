package com.adelvanchik.mydays.Data.filter

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.adelvanchik.mydays.Domain.entity.FilterData
import com.adelvanchik.mydays.Domain.repositiry.DayFilterDataRepository

class DayFilterDataImpl(private val context: Context): DayFilterDataRepository {

    private val sharedPreferences = context.getSharedPreferences(
        SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    override suspend fun getDayFilterData(): FilterData {
        return FilterData(
            monthStart = sharedPreferences.getInt(KEY_MONTH_START,FilterData.DEFAULT_MONTH_START),
            monthEnd = sharedPreferences.getInt(KEY_MONTH_END,FilterData.DEFAULT_MONTH_END),
            yearStart = sharedPreferences.getInt(KEY_YEAR_START,FilterData.DEFAULT_YEAR_START),
            yearEnd = sharedPreferences.getInt(KEY_YEAR_END,FilterData.DEFAULT_YEAR_END),
        )
    }

    override suspend fun saveDayFilterData(filterData: FilterData) {
        sharedPreferences.edit().putInt(KEY_MONTH_START, filterData.monthStart).apply()
        sharedPreferences.edit().putInt(KEY_MONTH_END, filterData.monthEnd).apply()
        sharedPreferences.edit().putInt(KEY_YEAR_START, filterData.yearStart).apply()
        sharedPreferences.edit().putInt(KEY_YEAR_END, filterData.yearEnd).apply()
        notifyFilterDataLiveData.postValue(Unit)
    }

    override fun notifyDataFilter(): MutableLiveData<Unit> {
        return notifyFilterDataLiveData
    }

    companion object {
        private const val KEY_MONTH_START = "month_start"
        private const val KEY_MONTH_END = "month_end"
        private const val KEY_YEAR_START = "year_start"
        private const val KEY_YEAR_END = "year_end"
        private const val SHARED_PREFERENCES_NAME = "shared_pref_fpr_data_filter"

        private val notifyFilterDataLiveData = MutableLiveData<Unit>()
    }
}