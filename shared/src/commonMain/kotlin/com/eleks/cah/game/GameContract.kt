package com.eleks.cah.game

import com.eleks.cah.base.UiEffect
import com.eleks.cah.base.UiState
import com.eleks.cah.domain.model.AnswerCard
import com.eleks.cah.domain.model.AnswerCardID
import com.eleks.cah.domain.model.GameRoom
import com.eleks.cah.domain.model.GameRound
import com.eleks.cah.domain.model.Player

interface GameContract {
    data class State(
        private val room: GameRoom? = null,
        val round: GameRound? = null,
        val me: Player? = null,
        val players: List<Player>? = emptyList()
    ) : UiState

    sealed class Effect : UiEffect {
        sealed class Navigation : Effect() {
            object YourCards : Navigation()
            object PreRound : Navigation()
            object Round : Navigation()
            object Voting : Navigation()
            object Results : Navigation()
        }
    }
}