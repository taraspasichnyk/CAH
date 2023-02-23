package com.eleks.cah.game

import com.eleks.cah.base.BaseViewModel
import com.eleks.cah.domain.model.*
import com.eleks.cah.domain.usecase.answer.AnswerUseCase
import com.eleks.cah.domain.usecase.room.GetRoomUseCase
import com.eleks.cah.domain.usecase.vote.VoteUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
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

    val me: StateFlow<Player?> = state.mapNotNull { it.room?.getSelf() }
        .stateIn(scope, SharingStarted.Eagerly, null)
    val round: StateFlow<GameRound?> = state.mapNotNull { it.room?.currentRound }
        .stateIn(scope, SharingStarted.Eagerly, null)

    /**
     * Pushing navigation effects (PreRound -> wait -> Your Cards )
     */
    init {
        scope.launch {
            showPreRound()
            delay(PRE_ROUND_DELAY)
            showYourCards()
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

    /**
     * Save answers by their ids and navigate to voting state if successful
     * @param answerCardIds list of strings to answer for current round
     */
    fun saveAnswers(answerCardIds: List<AnswerCardID>) {
        scope.launch {
            answerWith(roomId, playerId, answerCardIds)
            setEffect { GameContract.Effect.Navigation.Voting }
        }
    }

    /**
     * Save scores
     * @param answerCardWithVotes list of Answers which include id of voting player list of answers and score
     */
    fun saveScores(answerCardWithVotes: List<RoundPlayerAnswer>) {
        scope.launch {
            answerCardWithVotes.forEach {
                voteWith(roomId, it.playerID, it.score)
            }
        }
    }

    /**
     * Show preround screen with round number
     */
    fun showPreRound() {
        setEffect { GameContract.Effect.Navigation.PreRound }
    }

    /**
     * Show voting screen with available scores
     */
    fun showVoting() {
        setEffect { GameContract.Effect.Navigation.Round }
    }

    /**
     * Shows current round screen
     */
    fun showRound() {
        setEffect { GameContract.Effect.Navigation.Round }
    }

    /**
     * Shows list of available answers
     */
    fun showYourCards() {
        setEffect { GameContract.Effect.Navigation.YourCards }
    }

    private fun GameRoom.getSelf() = players.firstOrNull { it.id == playerId }

    companion object {
        private const val PRE_ROUND_DELAY = 3000L
    }

    fun getSelfFromState(gameState: GameContract.State): Player? {
        return gameState.room?.players?.firstOrNull { it.id == playerId }
    }
}