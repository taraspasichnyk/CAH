package com.eleks.cah

import com.eleks.cah.mvi.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class GameViewModel() {

    private val state:MutableStateFlow<GameState> = MutableStateFlow(GameState.InMenu)
//    val reducer = GameReducer()

    init {
//        state.tryEmit(reducer.state.value)
    }


    fun onNewGameClick() {
        state.tryEmit(GameState.InRoomCreation)
//        reducer.sendEvent(GameEvent.NewGame)
    }

    fun observeState() = AnyFlow(state)
}