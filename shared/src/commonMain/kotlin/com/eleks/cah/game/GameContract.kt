package com.eleks.cah.game

import com.eleks.cah.base.UiEffect
import com.eleks.cah.base.UiState
import com.eleks.cah.domain.model.GameRoom

interface GameContract   {
    data class State(
        val room: GameRoom? = null
    ) : UiState

    sealed class Effect : UiEffect {


        sealed class Navigation : Effect() {
            object YourCards : Navigation()
            object PreRound : Navigation()
            object Round : Navigation()
            object Voting : Navigation()
        }
    }
}