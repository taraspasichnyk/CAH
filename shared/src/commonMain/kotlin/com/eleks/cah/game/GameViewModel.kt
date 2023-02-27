package com.eleks.cah.game

import com.eleks.cah.base.BaseViewModel
import com.eleks.cah.domain.model.AnswerCardID
import com.eleks.cah.domain.model.GameRoom
import com.eleks.cah.domain.model.GameRound
import com.eleks.cah.domain.model.RoundPlayerAnswer
import com.eleks.cah.domain.usecase.answer.AnswerUseCase
import com.eleks.cah.domain.usecase.room.GetRoomUseCase
import com.eleks.cah.domain.usecase.vote.VoteUseCase
import com.eleks.cah.game.GameContract.Effect.Navigation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GameViewModel(
    private val roomId: String,
    private val playerId: String,
) : BaseViewModel<GameContract.State, GameContract.Effect>(GameContract.State(null)),
    KoinComponent {

    private val subscribeToRoomChanges: GetRoomUseCase by inject()

    private val answerWith: AnswerUseCase by inject()
    private val voteWith: VoteUseCase by inject()

    private var isNewRound: Boolean = true

    /**
     * Pushing navigation effects (PreRound -> wait -> Your Cards )
     */
    init {
        scope.launch {
            subscribeToRoomChanges(roomId).collectLatest { newRoom ->
                val oldGameState = state.value

                if (oldGameState.round != null && oldGameState.round.number != newRoom?.currentRound?.number) {
                    isNewRound = true
                    showRound()
                }

                setState {
                    oldGameState.copy(
                        room = newRoom,
                        me = newRoom?.getSelf(),
                        round = newRoom?.currentRound
                    )
                }
                if (oldGameState.round?.state != GameRound.GameRoundState.VOTING
                    && newRoom?.currentRound?.state == GameRound.GameRoundState.VOTING
                ) {
                    setEffect { Navigation.Voting }
                }
            }
        }
    }

    /**
     * Save answers by their ids and navigate to voting state if successful
     * @param answerCardIds list of strings to answer for current round
     */
    fun saveAnswers(answerCardIds: List<AnswerCardID>) {
        scope.launch {
            answerWith(roomId, playerId, answerCardIds)
        }
    }

    /**
     * Save scores
     * @param answerCardWithVotes list of Answers which include id of voting player list of answers and score
     */
    fun saveScores(answerCardWithVotes: List<RoundPlayerAnswer>) {
        scope.launch {
            answerCardWithVotes.forEach {
                voteWith(roomId, playerId, it.playerID, it.score)
            }
        }
    }

    /**
     * Show preround screen with round number
     */
    fun showPreRound() {
        setEffect { Navigation.PreRound }
    }

    /**
     * Show voting screen with available scores
     */
    fun showVoting() {
        setEffect { Navigation.Round }
    }

    /**
     * Shows current round screen
     */
    fun showRound() {
        scope.launch {
            if (isNewRound) {
                setEffect { Navigation.PreRound }
                delay(PRE_ROUND_DELAY)
                isNewRound = false
            }
            setEffect { Navigation.Round }
        }
    }

    /**
     * Shows list of available answers
     */
    fun showYourCards() {
        setEffect { Navigation.YourCards }
    }

    private fun GameRoom.getSelf() = players.firstOrNull { it.id == playerId }

    companion object {
        private const val PRE_ROUND_DELAY = 3000L
    }
}