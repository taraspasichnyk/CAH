package com.eleks.cah.game

import com.eleks.cah.base.BaseViewModel

class GameViewModel : BaseViewModel<GameContract.GameState, GameContract.GameEvent, GameContract.GameEffect>(GameContract.GameState.InMenu) {
    override fun handleEvents(event: GameContract.GameEvent) {
        when(event){
            GameContract.GameEvent.NewGame -> setEffect { GameContract.GameEffect }
        }
    }

}