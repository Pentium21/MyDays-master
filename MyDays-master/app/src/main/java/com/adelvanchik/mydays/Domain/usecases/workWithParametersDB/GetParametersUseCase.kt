package com.adelvanchik.mydays.Domain.usecases.workWithParametersDB

import com.adelvanchik.mydays.Domain.entity.Parameters
import com.adelvanchik.mydays.Domain.repositiry.ParametersRepository

class GetParametersUseCase(private var parametersRepository: ParametersRepository) {
    suspend operator fun invoke(data: Int): Parameters {
        return parametersRepository.getParameters(data)
    }
}