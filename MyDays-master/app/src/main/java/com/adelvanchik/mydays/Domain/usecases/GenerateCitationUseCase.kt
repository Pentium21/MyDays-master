package com.adelvanchik.mydays.Domain.usecases

import com.adelvanchik.mydays.Domain.entity.CitationModel
import com.adelvanchik.mydays.Domain.repositiry.GenerateCitationRepository

class GenerateCitationUseCase(private val generateRepository: GenerateCitationRepository) {
    operator fun invoke(): CitationModel {
        return generateRepository.generateCitation()
    }
}