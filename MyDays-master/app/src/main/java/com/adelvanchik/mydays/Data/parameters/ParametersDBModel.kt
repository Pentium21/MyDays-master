package com.adelvanchik.mydays.Data.parameters

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adelvanchik.mydays.Data.parameters.ParametersDBModel.Companion.TABLE_NAME
import com.adelvanchik.mydays.Domain.entity.Parameters

@Entity(tableName = TABLE_NAME)
data class ParametersDBModel(
    @PrimaryKey
    val data: Int,
    val day: Short,
    val month: Short,
    val year: Short,
    val weight: Int = Parameters.DEFAULT_VALUE,
    val height: Int = Parameters.DEFAULT_VALUE,
) {
    companion object {
        const val TABLE_NAME = "parameters_table"
    }
}