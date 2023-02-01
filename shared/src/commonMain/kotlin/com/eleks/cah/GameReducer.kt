package com.eleks.cah

import com.eleks.cah.mvi.Reducer

class GameReducer : Reducer<GameState, GameEvent>(GameState.InMenu) {
    override fun reduce(oldState: GameState, event: GameEvent) {
        if (event is GameEvent.NewGame) {
            setState(GameState.InRoomCreation)
        }
    }
}