package com.adelvanchik.mydays.Domain.usecases.workWithParametersDB

import com.adelvanchik.mydays.Domain.entity.Parameters
import com.adelvanchik.mydays.Domain.repositiry.ParametersRepository

class AddParametersUseCase(private var parametersRepository: ParametersRepository) {
    suspend operator fun invoke(parameters: Parameters) {
        parametersRepository.addParameters(parameters)
    }
}