package com.eleks.cah.menu

import com.eleks.cah.base.BaseViewModel

class MenuViewModel : BaseViewModel<MenuContract.State, MenuContract.Effect>(
    MenuContract.State()
) {

    fun onNewGameSelected() {
        setEffect { MenuContract.Effect.Navigation.NewGameScreen }
    }

    fun onJoinGameSelected() {
        setEffect { MenuContract.Effect.Navigation.JoinGameScreen }
    }

    fun onSettingsSelected() {
        setEffect { MenuContract.Effect.Navigation.SettingsScreen }
    }

    fun onExitSelected() {
        setEffect { MenuContract.Effect.Navigation.SettingsScreen }
    }

    companion object {
        const val NAVIGATION_EFFECTS_KEY = "menu_navigation_effects"
    }
}