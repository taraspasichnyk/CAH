package com.eleks.cah.domain.model

import kotlinx.serialization.Serializable

data class Player(
    val id: PlayerID,
    val nickname: String,
    val isGameOwner: Boolean,
    val cards: List<AnswerCard>,
    val score: Int,
    val state: PlayerState,
) {
    @Serializable
    enum class PlayerState {
        NOT_READY, READY, ANSWERING, VOTING, VOTE_SUBMITTED
    }
}

