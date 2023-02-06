package com.eleks.cah.menu

import com.eleks.cah.base.BaseViewModel
import com.eleks.cah.game.GameContract

class MenuViewModel : BaseViewModel<MenuContract.State, MenuContract.Event, MenuContract.Effect>(
    MenuContract.State()
) {
    override fun handleEvents(event: MenuContract.Event) {
        when (event) {
            is MenuContract.Event.StartNewGame -> setEffect { MenuContract.Effect.Navigation.NewGameScreen }
            is MenuContract.Event.JoinToExistingGame -> setEffect { MenuContract.Effect.Navigation.JoinGameScreen }
            is MenuContract.Event.OpenSettingsScreen -> setEffect { MenuContract.Effect.Navigation.SettingsScreen }
            is MenuContract.Event.Exit -> setEffect { MenuContract.Effect.Navigation.Exit }
        }
    }

    fun onNewGameSelected() {
        setEvent(MenuContract.Event.StartNewGame)
    }

    fun onJoinGameSelected() {
        setEvent(MenuContract.Event.JoinToExistingGame)
    }

    fun onSettingsSelected() {
        setEvent(MenuContract.Event.OpenSettingsScreen)
    }

    fun onExitSelected() {
        setEvent(MenuContract.Event.Exit)
    }

    companion object{
        const val NAVIGATION_EFFECTS_KEY = "NAVIGATION_EFFECTS_KEY"
    }
}