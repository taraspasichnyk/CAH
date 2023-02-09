package com.eleks.cah.game

import com.eleks.cah.base.BaseViewModel
import org.koin.core.component.KoinComponent

class GameViewModel : BaseViewModel<GameContract.GameState, GameContract.GameEffect>(GameContract.GameState.InMenu), KoinComponent {

}