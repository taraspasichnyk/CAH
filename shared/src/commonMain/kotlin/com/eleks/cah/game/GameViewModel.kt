package com.eleks.cah.game

import com.eleks.cah.base.BaseViewModel
import com.eleks.cah.domain.model.AnswerCardID
import com.eleks.cah.domain.usecase.answer.AnswerUseCase
import com.eleks.cah.domain.usecase.next_round.StartNextRoundUseCase
import com.eleks.cah.domain.usecase.room.GetRoomUseCase
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GameViewModel(
    private val roomId: String,
    private val playerId: String,
) : BaseViewModel<GameContract.State, GameContract.Effect>(GameContract.State(null)),
    KoinComponent {

    private val getRoom: GetRoomUseCase by inject()
    private val startRound: StartNextRoundUseCase by inject()
    private val answerWith: AnswerUseCase by inject()

    val me = state.map { it.room?.players?.firstOrNull { it.id == playerId } }

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

    fun startNewRound() {
        scope.launch {
            state.value.room?.id?.let {
                startRound(it)
            }
        }
    }

    fun answer(answerCardIds: List<AnswerCardID>) {
        scope.launch {
            Napier.d("roomId = $roomId, playerId = $playerId, $answerCardIds")
            answerWith(roomId, playerId, answerCardIds)
            setEffect { GameContract.Effect.Navigation.Voting }
        }
    }
}