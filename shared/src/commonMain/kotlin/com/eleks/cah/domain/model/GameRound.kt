package com.eleks.cah.domain.model

data class GameRound(
    val id: RoundID,
    val number: Int,
    val question: QuestionCardID,
    val answers: List<RoundPlayerAnswer>,
    val timer: Int,
    val state: GameRoundState
) {
    enum class GameRoundState {
        ACTIVE, VOTING, FINISHED
    }
}

