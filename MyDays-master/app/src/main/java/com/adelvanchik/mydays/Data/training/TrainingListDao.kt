package com.adelvanchik.mydays.Data.training

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adelvanchik.mydays.Domain.entity.Training

@Dao
interface TrainingListDao {

    @Query("SELECT * FROM ${TrainingDBModel.TABLE_NAME} WHERE data=:id LIMIT 1 ")
    fun getTrainingDay(id: Int): TrainingDBModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTrainingDay(training: TrainingDBModel)

    @Query("DELETE FROM ${TrainingDBModel.TABLE_NAME} WHERE data=:id")
    fun deleteTrainingDay(id: Int)

    @Query("SELECT * FROM ${TrainingDBModel.TABLE_NAME}")
    fun getListOfTrainingDay(): LiveData<List<TrainingDBModel>>
}