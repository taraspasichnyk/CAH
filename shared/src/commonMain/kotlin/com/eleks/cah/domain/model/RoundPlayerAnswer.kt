package com.eleks.cah.domain.model

data class RoundPlayerAnswer(
    val playerID: PlayerID,
    val playerAnswers: List<AnswerCard>,
    val score: Int
)

