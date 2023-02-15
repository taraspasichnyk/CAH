package com.eleks.cah.menu

import com.eleks.cah.base.BaseViewModel
import com.eleks.cah.menu.MenuContract.Effect.Navigation

class MenuViewModel : BaseViewModel<MenuContract.State, MenuContract.Effect>(
    MenuContract.State()
) {
    fun onNewGameSelected() {
        setEffect { Navigation.NewGameScreen }
    }

    fun onJoinGameSelected() {
        setEffect { Navigation.JoinGameScreen }
    }

    fun onExitSelected() {
        setEffect { Navigation.Exit }
    }
}