package com.eleks.cah.domain.model

data class GameRound(
    val id: RoundID,
    val number: Int,
    val masterCard: QuestionCard,
    val answers: List<RoundPlayerAnswer>,
    val state: GameRoundState
) {
    enum class GameRoundState {
        ACTIVE, VOTING, FINISHED
    }
}

