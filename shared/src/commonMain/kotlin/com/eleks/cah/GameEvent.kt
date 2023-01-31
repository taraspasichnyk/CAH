package com.eleks.cah

import com.eleks.cah.mvi.UiEvent

sealed class GameEvent : UiEvent {

    object NewGame : GameEvent()
}