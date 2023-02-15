package com.eleks.cah.game

import com.eleks.cah.base.BaseViewModel
import com.eleks.cah.domain.usecase.next_round.StartNextRoundUseCase
import com.eleks.cah.domain.usecase.room.GetRoomUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GameViewModel(
    private val roomId: String
) : BaseViewModel<GameContract.State, GameContract.Effect>(GameContract.State(null)),
    KoinComponent {

    private val getRoom: GetRoomUseCase by inject()
    private val startRound: StartNextRoundUseCase by inject()

    fun startNewRound() {
        scope.launch {
            state.value.room?.id?.let {
                startRound(it)
            }
        }
    }

    init {
        scope.launch {
            getRoom(roomId, this).collectLatest {
                setState {
                    state.value.copy(
                        room = it
                    )
                }
            }
        }
    }

}