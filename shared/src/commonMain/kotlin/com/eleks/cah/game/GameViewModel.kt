package com.eleks.cah.game

import com.eleks.cah.base.BaseViewModel
import com.eleks.cah.domain.model.AnswerCardID
import com.eleks.cah.domain.model.RoundPlayerAnswer
import com.eleks.cah.domain.usecase.answer.AnswerUseCase
import com.eleks.cah.domain.usecase.next_round.StartNextRoundUseCase
import com.eleks.cah.domain.usecase.room.GetRoomUseCase
import com.eleks.cah.domain.usecase.vote.VoteUseCase
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay
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
    private val answerWith: AnswerUseCase by inject()
    private val voteWith: VoteUseCase by inject()

    init {
        scope.launch {
            setEffect { GameContract.Effect.Navigation.PreRound }
            delay(5000L)
            setEffect { GameContract.Effect.Navigation.YourCards }
            delay(5000L)
            setEffect { GameContract.Effect.Navigation.Round }
        }
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

    fun saveAnswers(answerCardIds: List<AnswerCardID>) {
        scope.launch {
            Napier.d("PHASE 1: roomId = $roomId, playerId = $playerId, $answerCardIds")
            answerWith(roomId, playerId, answerCardIds)
            setEffect { GameContract.Effect.Navigation.Voting }
        }
    }

    fun saveScores(answerCardWithVotes: List<RoundPlayerAnswer>) {
        scope.launch {
            answerCardWithVotes.forEach {
                Napier.d("PHASE 2: roomId = $roomId, playerId = $playerId, $answerCardWithVotes")
                voteWith(roomId, it.playerID, it.score)
            }
        }
    }
}