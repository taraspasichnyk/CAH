package com.eleks.cah.game

import com.eleks.cah.base.BaseViewModel
import com.eleks.cah.domain.usecase.next_round.StartNextRoundUseCase
import com.eleks.cah.domain.usecase.room.GetRoomUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class GameViewModel(
    private val getRoom: GetRoomUseCase,
    private val startRound: StartNextRoundUseCase
) :
    BaseViewModel<GameContract.State, GameContract.Effect>(GameContract.State(null)),
    KoinComponent {
    fun startNewRound() {
        scope.launch {
            state.value.room?.id?.let {
                startRound(it)
            }
        }
    }

    init {
        scope.launch {
            getRoom("104462", this).collectLatest {
                setState {
                    state.value.copy(
                        room = it
                    )
                }
            }
        }
    }

}