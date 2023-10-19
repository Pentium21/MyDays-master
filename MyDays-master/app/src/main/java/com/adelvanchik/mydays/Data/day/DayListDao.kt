package com.adelvanchik.mydays.Data.day

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adelvanchik.mydays.Domain.entity.Thoughts

@Dao
interface DayListDao {

    @Query("SELECT * FROM ${DayDBModel.TABLE_NAME} " +
            "WHERE percOfCompletedTasks >= :minEffectiveness " +
            "and percOfCompletedTasks <=:maxEffectiveness and data>=:minData and data<=:maxData")
    fun getListOfDays(minEffectiveness: Short, maxEffectiveness: Short,
                      minData: Int, maxData: Int
    ): LiveData<List<DayDBModel>>

    @Query("SELECT data, day, month, year, thoughts from ${DayDBModel.TABLE_NAME} " +
            "WHERE thoughts <>:defaultValue")
    fun getListOfThoughts(defaultValue: String): LiveData<List<Thoughts>>

    @Query("SELECT data, day, month, year, thoughts from ${DayDBModel.TABLE_NAME} " +
            "WHERE data=:id LIMIT 1")
    fun getThoughtsDay(id: Int): Thoughts

    @Query("SELECT * FROM ${DayDBModel.TABLE_NAME} WHERE data =:dayId LIMIT 1")
    fun getDay(dayId: Int): DayDBModel

    @Query("DELETE FROM ${DayDBModel.TABLE_NAME} WHERE data =:dayId")
    fun deleteDay(dayId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDay(day: DayDBModel)

    @Query("SELECT EXISTS(SELECT * FROM ${DayDBModel.TABLE_NAME} WHERE data=:dayId)")
    fun isHaveDayInTable(dayId: Int): Boolean

    @Query("SELECT MIN(year) FROM ${DayDBModel.TABLE_NAME}")
    fun getMinYearInTableDay(): Short?

    @Query("UPDATE ${DayDBModel.TABLE_NAME} SET thoughts =:value WHERE data=:dayId")
    fun updateThoughtsForDay(dayId: Int, value: String)

}