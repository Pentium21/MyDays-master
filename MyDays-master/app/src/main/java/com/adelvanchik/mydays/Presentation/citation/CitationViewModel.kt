package com.adelvanchik.mydays.Presentation.citation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adelvanchik.mydays.Data.citation.GenerateCitationRepositoryImpl
import com.adelvanchik.mydays.Domain.entity.CitationModel
import com.adelvanchik.mydays.Domain.usecases.GenerateCitationUseCase

class CitationViewModel: ViewModel() {

    private var _citation = MutableLiveData<CitationModel>()
    val citation: LiveData<CitationModel>
    get() = _citation

    fun generateCitation() {
        _citation.value = generateCitationUseCase()
    }

    companion object {
        private val generateRepositoryImpl = GenerateCitationRepositoryImpl()
        private val generateCitationUseCase = GenerateCitationUseCase(generateRepositoryImpl)
    }
}