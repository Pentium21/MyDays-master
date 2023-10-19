package com.adelvanchik.mydays.Domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Day(
    val data: Int,
    val day: Short,
    val month: Short,
    val year: Short,
    val dataStringFormat: String,
    val durationOfSleep: Short = DEFAULT_SHORT_VALUE,
    val health: Short = DEFAULT_SHORT_VALUE,
    val percOfCompletedTasks: Short = DEFAULT_SHORT_VALUE,
    val countOfCompletedTasks: Short = DEFAULT_SHORT_VALUE,
    val countOfPlannedTasks: Short = DEFAULT_SHORT_VALUE,
    val amountOfWater: Float = DEFAULT_FLOAT_VALUE,
    val achievements: String = DEFAULT_STRING_VALUE,
    val thoughts: String = DEFAULT_STRING_VALUE,
    var training: String = DEFAULT_STRING_VALUE,
) : Parcelable {
    companion object {
        const val DEFAULT_SHORT_VALUE: Short = -7;
        const val DEFAULT_FLOAT_VALUE: Float = -7f;
        const val DEFAULT_STRING_VALUE: String = "";

        const val EFFECTIVENESS_1_START: Short = 0
        const val EFFECTIVENESS_1_END: Short = 39
        const val EFFECTIVENESS_2_START: Short = 40
        const val EFFECTIVENESS_2_END: Short = 69
        const val EFFECTIVENESS_3_START: Short = 70
        const val EFFECTIVENESS_3_END: Short = 100

    }
}