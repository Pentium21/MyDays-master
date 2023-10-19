package com.adelvanchik.mydays.Domain.repositiry

import androidx.lifecycle.LiveData
import com.adelvanchik.mydays.Domain.entity.Parameters

interface ParametersRepository {

    fun getListOfParameters(): LiveData<List<Parameters>>

    suspend fun addParameters(parameters: Parameters)

    suspend fun deleteParameters(data: Int)

    suspend fun getParameters(data: Int): Parameters

    suspend fun getCurrentParameters(): Parameters
}