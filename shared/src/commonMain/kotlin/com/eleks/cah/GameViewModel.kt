package com.eleks.cah

import com.eleks.cah.mvi.BaseViewModel

class GameViewModel : BaseViewModel<GameState, GameEvent>() {

    override val reducer = GameReducer()

    fun onNewGameClick() {
        reducer.sendEvent(GameEvent.NewGame)
    }

}