package com.adelvanchik.mydays.Data.day

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adelvanchik.mydays.Data.day.DayDBModel.Companion.TABLE_NAME
import com.adelvanchik.mydays.Domain.entity.Day

@Entity(tableName = TABLE_NAME)
data class DayDBModel(
    @PrimaryKey
    val data: Int,
    val day: Short,
    val month: Short,
    val year: Short,
    val dataStringFormat: String,
    val durationOfSleep: Short = DEFAULT_SHORT_VALUE,
    val health: Short = DEFAULT_SHORT_VALUE,
    val percOfCompletedTasks: Short = DEFAULT_SHORT_VALUE,
    val countOfCompletedTasks: Short = Day.DEFAULT_SHORT_VALUE,
    val countOfPlannedTasks: Short = Day.DEFAULT_SHORT_VALUE,
    val amountOfWater: Float = DEFAULT_FLOAT_VALUE,
    val achievements: String = DEFAULT_STRING_VALUE,
    val thoughts: String = DEFAULT_STRING_VALUE,
    var training: String = Day.DEFAULT_STRING_VALUE,
) {
    companion object {
        const val TABLE_NAME: String = "day_table"
        const val DEFAULT_SHORT_VALUE: Short = -7;
        const val DEFAULT_FLOAT_VALUE: Float = -7f;
        const val DEFAULT_STRING_VALUE: String = "";
    }
}