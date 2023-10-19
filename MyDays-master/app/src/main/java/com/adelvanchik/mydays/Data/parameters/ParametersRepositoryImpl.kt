package com.adelvanchik.mydays.Data.parameters

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.adelvanchik.mydays.Data.AppDatabase
import com.adelvanchik.mydays.Data.training.TrainingDayRepositoryImpl
import com.adelvanchik.mydays.Domain.entity.Parameters
import com.adelvanchik.mydays.Domain.entity.Training
import com.adelvanchik.mydays.Domain.repositiry.ParametersRepository
import java.util.*

class ParametersRepositoryImpl(val application: Application) : ParametersRepository {

    private val parametersMapper = ParametersMapper()
    private val daoList = AppDatabase.getInstance(application = application).ParametersListDao()

    override fun getListOfParameters(): LiveData<List<Parameters>> {
        return Transformations.map(
            daoList.getListOfParameters()) {
            parametersMapper.listParametersDBModelToParameters(it).sortedByDescending { it.data }
        }
    }

    override suspend fun addParameters(parameters: Parameters) {
        daoList.addParameters(parametersMapper.entityParametersToParametersDBModel(parameters))
    }

    override suspend fun deleteParameters(data: Int) {
        daoList.deleteParameters(data)
    }

    override suspend fun getParameters(data: Int): Parameters {
        return parametersMapper.entityParametersDBModelToParameters(daoList.getParameters(data)!!)
    }

    override suspend fun getCurrentParameters(): Parameters {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        // месяц исчисляется с нуля, добавляем единицу
        val month = c.get(Calendar.MONTH) + ADD_ONE_FOR_MONTH
        val day = c.get(Calendar.DAY_OF_MONTH)

        // создаем id Дня
        // для 22 01 2022 id будет равен 2022/22/01 (год/месяц/число) без слэша
        val id = year * 10000 + month * 100 + day
        val parameters = daoList.getParameters(id)
        if (parameters!=null) {
            return parametersMapper.entityParametersDBModelToParameters(parameters)
        }
        return Parameters(
            data = id,
            day = day.toShort(),
            month = month.toShort(),
            year = year.toShort()
        )
    }

    companion object {
        private const val ADD_ONE_FOR_MONTH = 1
    }


}