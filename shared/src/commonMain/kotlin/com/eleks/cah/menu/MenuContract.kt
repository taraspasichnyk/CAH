package com.eleks.cah.menu

import com.eleks.cah.base.UiEffect
import com.eleks.cah.base.UiState

interface MenuContract {
    data class State(
        private val isLoading: Boolean = false
    ) : UiState

    sealed class Effect : UiEffect {

        sealed class Navigation : Effect() {
            object NewGameScreen : Navigation()
            object JoinGameScreen : Navigation()
            object Exit: Navigation()
        }
    }
}