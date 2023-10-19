package com.adelvanchik.mydays.Domain.usecases.workWithParametersDB

import com.adelvanchik.mydays.Domain.repositiry.ParametersRepository

class DeleteParametersUseCase(private var parametersRepository: ParametersRepository) {
    suspend operator fun invoke(id: Int) {
        parametersRepository.deleteParameters(id)
    }
}