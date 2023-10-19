package com.adelvanchik.mydays.Data.parameters

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ParametersListDao {

    @Query("SELECT * FROM ${ParametersDBModel.TABLE_NAME}")
    fun getListOfParameters(): LiveData<List<ParametersDBModel>>

    @Query("SELECT * FROM ${ParametersDBModel.TABLE_NAME} WHERE data=:data limit 1")
    fun getParameters(data: Int): ParametersDBModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addParameters(parameters: ParametersDBModel)

    @Query("DELETE FROM ${ParametersDBModel.TABLE_NAME} WHERE data=:data")
    fun deleteParameters(data: Int)
}

