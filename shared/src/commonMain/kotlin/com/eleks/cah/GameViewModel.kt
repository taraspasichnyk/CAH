package com.eleks.cah

import com.eleks.cah.mvi.BaseViewModel
import com.eleks.cah.mvi.Reducer
import kotlinx.coroutines.flow.MutableStateFlow

class GameViewModel : BaseViewModel<GameState, GameEvent>() {

    override val reducer: Reducer<GameState, GameEvent> = GameReducer()

    fun onNewGameClick() {
        reducer.sendEvent(GameEvent.NewGame)
    }
}