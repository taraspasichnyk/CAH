package com.eleks.cah.game

import com.eleks.cah.base.UiEffect
import com.eleks.cah.base.UiState

interface GameContract   {
    sealed class GameState : UiState {
        object InMenu : GameState()
        object InRoomCreation : GameState()
        object InLobby : GameState()
        object InSettings: GameState()

        sealed class Game: GameState(){

        }

        sealed class Lobby: GameState(){

        }
    }

    object GameEffect: UiEffect
}