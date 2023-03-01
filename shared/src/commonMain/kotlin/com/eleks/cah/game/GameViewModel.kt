package com.eleks.cah.game

import com.eleks.cah.base.BaseViewModel
import com.eleks.cah.domain.model.*
import com.eleks.cah.domain.usecase.answer.AnswerUseCase
import com.eleks.cah.domain.usecase.next_round.StartNextRoundUseCase
import com.eleks.cah.domain.usecase.player_state.UpdatePlayerStateUseCase
import com.eleks.cah.domain.usecase.room.GetRoomUseCase
import com.eleks.cah.domain.usecase.vote.VoteUseCase
import com.eleks.cah.game.GameContract.Effect.Navigation
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GameViewModel(
    private val roomId: String,
    private val playerId: String,
) : BaseViewModel<GameContract.State, GameContract.Effect>(GameContract.State()),
    KoinComponent {

    private val subscribeToRoomChanges: GetRoomUseCase by inject()

    private val answerWith: AnswerUseCase by inject()
    private val voteWith: VoteUseCase by inject()
    private val updatePlayerState: UpdatePlayerStateUseCase by inject()
    private val startNextRound: StartNextRoundUseCase by inject()

    private var isNewRound: Boolean = true

    /**
     * Pushing navigation effects (PreRound -> wait -> Your Cards )
     */
    init {
        scope.launch {
            subscribeToRoomChanges(roomId).collectLatest { newRoom ->
                val oldGameState = state.value

                newRoom?.let { tryGoNextRound(it) }

                val isNewRoundStarted = oldGameState.round != null
                        && oldGameState.round.number != newRoom?.currentRound?.number
                val isGameFinished = oldGameState.round != null && newRoom != null
                        && newRoom.currentRound == null
                if (isNewRoundStarted || isGameFinished) {
                    Napier.d(tag = "TestTag", message = "isNewRound = $isNewRoundStarted, showResults")
                    isNewRound = isNewRoundStarted
                    setEffect { Navigation.Results }
                }

                setState {
                    oldGameState.copy(
                        room = newRoom,
                        me = newRoom?.getSelf(),
                        round = newRoom?.currentRound?.copy(
                            playerCards = newRoom.currentRound.playerCards.filter {
                                it.playerID != me?.id
                            }
                        ),
                        players = newRoom?.players
                    )
                }
                if (oldGameState.round?.state != GameRound.GameRoundState.VOTING
                    && newRoom?.currentRound?.state == GameRound.GameRoundState.VOTING
                ) {
                    setEffect { Navigation.Voting }
                }
                else if(oldGameState.round?.state != GameRound.GameRoundState.FINISHED
                    && newRoom?.currentRound?.state == GameRound.GameRoundState.FINISHED) {

                    setEffect { Navigation.RoundLeaderBoard }
                }
            }
        }
    }

    private fun tryGoNextRound(
        newRoom: GameRoom
    ) {
        scope.launch {
            Napier.d(tag = "TestTag", message = "tryGoNextRound: areAllPlayersVoted = ${newRoom.areAllPlayersVoted()}")
            if (newRoom.areAllPlayersVoted()) {
                val gameOwner = newRoom.gameOwner ?: return@launch
                val playerOwnsGame = gameOwner.id == playerId
                if (playerOwnsGame) {
                    Napier.d(tag = "TestTag", message = "tryGoNextRound: startNextRound")
                    startNextRound.invoke(roomId)
                }
            }
        }
    }

    private fun GameRoom.areAllPlayersVoted(): Boolean {
        return players.all { it.state == Player.PlayerState.VOTE_SUBMITTED }
    }

    /**
     * Save answers by their ids and navigate to voting state if successful
     * @param answerCardIds list of strings to answer for current round
     */
    fun saveAnswers(answerCardIds: List<AnswerCardID>) {
        scope.launch {
            answerWith(roomId, playerId, answerCardIds)
            updatePlayerState.invoke(roomId, playerId, Player.PlayerState.VOTING)
        }
    }

    /**
     * Save scores
     * @param answerCardWithVotes list of Answers which include id of voting player list of answers and score
     */
    fun saveScores(answerCardWithVotes: List<RoundPlayerAnswer>) {
        Napier.d(tag = "TestTag", message = "saveScores: answerCardWithVotes = ${answerCardWithVotes.joinToString()}")
        scope.launch {
            answerCardWithVotes.forEach {
                voteWith(roomId, playerId, it.playerID, it.score)
            }
            val votedForAllAnswers = answerCardWithVotes
                .filter { it.playerID != playerId }
                .all { it.score != 0 }
            if (votedForAllAnswers) {
                updatePlayerState.invoke(roomId, playerId, Player.PlayerState.VOTE_SUBMITTED)
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

    fun startNewRound() {
        scope.launch {
            startNextRound(roomId)
        }
    }

    private fun GameRoom.getSelf() = players.firstOrNull { it.id == playerId }

    companion object {
        private const val PRE_ROUND_DELAY = 3000L
    }
}