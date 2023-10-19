package com.adelvanchik.mydays.Data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.adelvanchik.mydays.Data.day.DayDBModel
import com.adelvanchik.mydays.Data.day.DayListDao
import com.adelvanchik.mydays.Data.training.TrainingDBModel
import com.adelvanchik.mydays.Data.training.TrainingListDao
import com.adelvanchik.mydays.Data.parameters.ParametersDBModel
import com.adelvanchik.mydays.Data.parameters.ParametersListDao

@Database(entities = [DayDBModel::class, TrainingDBModel::class, ParametersDBModel::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun DayListDao(): DayListDao
    abstract fun TrainingListDao(): TrainingListDao
    abstract fun ParametersListDao(): ParametersListDao

    companion object {

        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()
        private const val NAME_DB = "day_db"

        fun getInstance(application: Application): AppDatabase {

            INSTANCE?.let {
                return it
            }

            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }

                val db = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    NAME_DB,
                ).build()
                INSTANCE = db
                return db

            }
        }
    }
}