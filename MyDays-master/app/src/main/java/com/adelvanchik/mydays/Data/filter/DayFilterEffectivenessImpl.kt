package com.adelvanchik.mydays.Data.filter

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.adelvanchik.mydays.Domain.entity.FilterEffectiveness
import com.adelvanchik.mydays.Domain.repositiry.DayFilterEffectivenessRepository

class DayFilterEffectivenessImpl(private val context: Context) : DayFilterEffectivenessRepository {

    private val sharedPreferences = context.getSharedPreferences(
        SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    override suspend fun getDayFilterEffectiveness(): FilterEffectiveness {
        return FilterEffectiveness(
            isIncludeEffectivenessEmpty = sharedPreferences.getBoolean(
                KEY_EFFECTIVENESS_EMPTY, DEFAULT_VALUE),
            isIncludeEffectiveness1 = sharedPreferences.getBoolean(
                KEY_EFFECTIVENESS_1, DEFAULT_VALUE),
            isIncludeEffectiveness2 = sharedPreferences.getBoolean(
                KEY_EFFECTIVENESS_2, DEFAULT_VALUE),
            isIncludeEffectiveness3 = sharedPreferences.getBoolean(
                KEY_EFFECTIVENESS_3, DEFAULT_VALUE),
        )
    }

    override suspend fun saveDayFilterEffectiveness(filterEffectiveness: FilterEffectiveness) {
        sharedPreferences.edit().putBoolean(
            KEY_EFFECTIVENESS_EMPTY, filterEffectiveness.isIncludeEffectivenessEmpty).apply()

        sharedPreferences.edit().putBoolean(
            KEY_EFFECTIVENESS_1, filterEffectiveness.isIncludeEffectiveness1).apply()

        sharedPreferences.edit().putBoolean(
            KEY_EFFECTIVENESS_2, filterEffectiveness.isIncludeEffectiveness2).apply()

        sharedPreferences.edit().putBoolean(
            KEY_EFFECTIVENESS_3, filterEffectiveness.isIncludeEffectiveness3).apply()

        notifyFilterEffectivenessLiveData.postValue(Unit)
    }

    override fun notifyEffectivenessFilter(): MutableLiveData<Unit> {
        return notifyFilterEffectivenessLiveData
    }

    companion object {
        private const val KEY_EFFECTIVENESS_EMPTY = "effectiveness_empty"
        private const val KEY_EFFECTIVENESS_1 = "effectiveness_1"
        private const val KEY_EFFECTIVENESS_2 = "effectiveness_2"
        private const val KEY_EFFECTIVENESS_3 = "effectiveness_3"
        private const val DEFAULT_VALUE = true
        private const val SHARED_PREFERENCES_NAME = "shared_pref_name_for_effectiveness"

        private val notifyFilterEffectivenessLiveData = MutableLiveData<Unit>()
    }
}