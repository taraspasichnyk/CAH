package com.eleks.cah.menu

import com.eleks.cah.base.BaseViewModel
import com.eleks.cah.base.ScopedViewModel

class MenuViewModel : BaseViewModel<MenuContract.State, MenuContract.Event, MenuContract.Effect>(
    MenuContract.State(false)
) {

    override fun handleEvents(event: MenuContract.Event) {
        when (event) {
            is MenuContract.Event.StartNewGame -> setEffect { MenuContract.Effect.Navigation.NewGameScreen }
            else -> return
        }
    }
}