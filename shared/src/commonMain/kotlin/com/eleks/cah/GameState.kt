package com.eleks.cah

import com.eleks.cah.mvi.UiState

sealed class GameState : UiState {
    object InMenu : GameState()
    object InRoomCreation : GameState()

    object InLobby : GameState()

    object InSettings: GameState()

}

