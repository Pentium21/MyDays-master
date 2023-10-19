package com.adelvanchik.mydays.Domain.repositiry

import com.adelvanchik.mydays.Domain.entity.CitationModel

interface GenerateCitationRepository {

    fun generateCitation(): CitationModel
}