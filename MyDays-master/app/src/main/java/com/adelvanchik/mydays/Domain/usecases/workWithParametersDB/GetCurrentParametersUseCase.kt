package com.adelvanchik.mydays.Domain.usecases.workWithParametersDB

import com.adelvanchik.mydays.Domain.entity.Parameters
import com.adelvanchik.mydays.Domain.repositiry.ParametersRepository

class GetCurrentParametersUseCase(private var parametersRepository: ParametersRepository) {
    suspend operator fun invoke(): Parameters {
        return parametersRepository.getCurrentParameters()
    }
}