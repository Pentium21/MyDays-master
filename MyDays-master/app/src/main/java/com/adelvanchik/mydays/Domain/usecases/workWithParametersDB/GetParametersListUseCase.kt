package com.adelvanchik.mydays.Domain.usecases.workWithParametersDB

import androidx.lifecycle.LiveData
import com.adelvanchik.mydays.Domain.entity.Parameters
import com.adelvanchik.mydays.Domain.repositiry.ParametersRepository

class GetParametersListUseCase(private var parametersRepository: ParametersRepository) {
    operator fun invoke(): LiveData<List<Parameters>> {
        return parametersRepository.getListOfParameters()
    }
}