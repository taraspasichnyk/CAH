package com.eleks.cah.menu

import com.eleks.cah.base.UiEffect
import com.eleks.cah.base.UiEvent
import com.eleks.cah.base.UiState

interface MenuContract {
    sealed class Event : UiEvent {
        object StartNewGame : Event()
        object JoinToExistingGame : Event()
    }

    data class State(
        private val isLoading: Boolean = false
    ) : UiState

    sealed class Effect : UiEffect {

        sealed class Navigation : Effect() {
            object NewGameScreen : Navigation()
        }
    }
}