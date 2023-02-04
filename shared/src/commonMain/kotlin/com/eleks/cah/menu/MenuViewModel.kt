package com.eleks.cah.menu

import com.eleks.cah.base.BaseViewModel
import com.eleks.cah.game.GameContract

class MenuViewModel : BaseViewModel<MenuContract.State, MenuContract.Event, MenuContract.Effect>(
    MenuContract.State()
) {
    override fun handleEvents(event: MenuContract.Event) {
        when (event) {
            is MenuContract.Event.StartNewGame -> setEffect { MenuContract.Effect.Navigation.NewGameScreen }
            else -> return
        }
    }

    fun onNewGameSelected() {
        setEvent(MenuContract.Event.StartNewGame)
    }

    fun onJoinGameSelected() {
        setEvent(MenuContract.Event.JoinToExistingGame)
    }
}