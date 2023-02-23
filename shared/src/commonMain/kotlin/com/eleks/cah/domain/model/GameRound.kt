package com.eleks.cah.domain.model

data class GameRound(
    val id: RoundID,
    val number: Int,
    val masterCard: QuestionCard,
    val playerCards: List<RoundPlayerAnswer>,
)

