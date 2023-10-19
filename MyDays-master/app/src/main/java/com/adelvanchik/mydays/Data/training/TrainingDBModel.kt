package com.adelvanchik.mydays.Data.training

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adelvanchik.mydays.Data.training.TrainingDBModel.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class TrainingDBModel(
    @PrimaryKey
    val data: Int,
    val day: Short,
    val month: Short,
    val year: Short,
    var training: String = DEFAULT_STRING_VALUE,
){
    companion object {
        const val TABLE_NAME = "training_table"
        const val DEFAULT_STRING_VALUE: String = "";
    }
}