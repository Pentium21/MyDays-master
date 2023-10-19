package com.adelvanchik.mydays.Data.citation

import com.adelvanchik.mydays.Domain.entity.CitationModel
import com.adelvanchik.mydays.Domain.repositiry.GenerateCitationRepository

class GenerateCitationRepositoryImpl: GenerateCitationRepository {

    override fun generateCitation(): CitationModel {

        return citationList.shuffled().first()
    }

    companion object {
        private val citationList: List<CitationModel> = listOf(
            CitationModel(
                "Успех - это идти от неудачи к неудаче, не теряя энтузиазма",
                "Уинстон Черчилль"),
            CitationModel(
                "Не считай дни, извлекай из них пользу",
                "Мухаммед Али"),
            CitationModel(
                "Лучше быть ручейком, который находится в движении, чем большим озером...",
                "Адель Гришин"),
            CitationModel(
                "Не ждите. Время никогда не будет подходящим",
                "Наполеон Хилл"),
            CitationModel(
                "Я не потерпел неудачу. Я просто нашел 10 000 способов, которые не работают",
                "Томас Эдисон"),
            CitationModel(
                "Вы должны выучить правила игры. А затем вы должны играть лучше, чем кто-либо другой",
                 "Альберт Эйнштейн"),
            CitationModel(
                "Лучшая месть - это огромный успех",
                "Фрэнк Синатра"),
        )
    }
}