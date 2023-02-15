package com.eleks.cah.domain.model

data class Player(
    val id: PlayerID,
    val nickname: String,
    val gameOwner:Boolean,
    val cards: List<AnswerCard>,
    val score: Int,
    val state: PlayerState,
) {
    enum class PlayerState {
        NOT_READY, READY, PLAYING, VOTING
    }
}

