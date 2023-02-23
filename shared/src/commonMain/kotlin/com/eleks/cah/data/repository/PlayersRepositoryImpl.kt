package com.eleks.cah.data.repository

import com.eleks.cah.data.extensions.roomOrException
import com.eleks.cah.data.model.GameRoomDTO
import com.eleks.cah.data.model.RoundPlayerAnswerDTO
import com.eleks.cah.domain.Constants.DB_REF_ANSWERS
import com.eleks.cah.domain.Constants.DB_REF_CURRENT_ROUND
import com.eleks.cah.domain.Constants.DB_REF_PLAYERS
import com.eleks.cah.domain.Constants.DB_REF_ROOMS
import com.eleks.cah.domain.Constants.DB_REF_STATE
import com.eleks.cah.domain.exceptions.PlayerNotFoundException
import com.eleks.cah.domain.exceptions.RoomNoCurrentRoundException
import com.eleks.cah.domain.model.*
import com.eleks.cah.domain.repository.PlayersRepository
import dev.gitlive.firebase.database.DatabaseReference
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.firstOrNull

class PlayersRepositoryImpl(
    databaseReference: DatabaseReference
) : PlayersRepository {
    private val roomsDbReference = databaseReference.child(DB_REF_ROOMS)

    override suspend fun updatePlayerState(
        roomID: RoomID,
        playerID: PlayerID,
        newState: Player.PlayerState
    ) {
        roomsDbReference.child(roomID)
            .child(DB_REF_PLAYERS)
            .child(playerID)
            .valueEvents
            .firstOrNull()
            ?.takeIf { it.exists }
            ?.let {
                changePlayerState(roomID, playerID, newState)
            } ?: throw PlayerNotFoundException(playerID)
    }

    private suspend fun changePlayerState(
        roomID: RoomID,
        playerID: PlayerID,
        newState: Player.PlayerState
    ) {
        roomsDbReference.child(roomID)
            .child(DB_REF_PLAYERS)
            .child(playerID)
            .child(DB_REF_STATE)
            .setValue(newState)
        Napier.d(
            tag = TAG,
            message = "Player $playerID state updated (newState = $newState) in room $roomID"
        )
    }

    override suspend fun makeAnswer(
        roomID: RoomID,
        playerID: PlayerID,
        playerAnswers: List<String>
    ) {
        roomsDbReference.roomOrException(roomID) {
            savePlayerAnswer(it, playerID, playerAnswers)
        }
    }

    private suspend fun savePlayerAnswer(
        gameRoomDto: GameRoomDTO,
        playerID: PlayerID,
        playerAnswers: List<String>
    ) {
        val playerAnswer = RoundPlayerAnswerDTO(
            playerID,
            playerAnswers
        )

        Napier.d(tag = TAG, message = "CREATE ANSWER $playerAnswer")

        val currentRound =
            gameRoomDto.currentRound ?: throw RoomNoCurrentRoundException(gameRoomDto.id)
        val updatedCurrentRound = currentRound.copy(
            answers = currentRound.answers + listOf(playerAnswer)
        )

        roomsDbReference.child(gameRoomDto.id)
            .child(DB_REF_CURRENT_ROUND)
            .setValue(updatedCurrentRound)
        Napier.d(
            tag = TAG,
            message = "Player ${playerAnswer.playerID}'s answer in round ${currentRound.number} saved"
        )
    }

    override suspend fun voteForAnswer(
        roomID: RoomID,
        voterID: PlayerID,
        playerID: PlayerID,
        score: Int
    ) {
        roomsDbReference.roomOrException(roomID) {
            updateAnswerScore(it, voterID, playerID, score)
        }
    }

    private suspend fun updateAnswerScore(
        gameRoomDto: GameRoomDTO,
        voterID: PlayerID,
        playerID: PlayerID,
        score: Int
    ) {
        val currentRound =
            gameRoomDto.currentRound ?: throw RoomNoCurrentRoundException(gameRoomDto.id)
        val updatedCurrentRoundAnswers = currentRound.answers.map {
            if (it.playerID == playerID) {
                val updatedPlayerScores = HashMap(it.scores).apply {
                    this[voterID] = score
                }
                it.copy(scores = updatedPlayerScores)
            } else {
                it
            }
        }
        roomsDbReference.child(gameRoomDto.id)
            .child(DB_REF_CURRENT_ROUND)
            .child(DB_REF_ANSWERS)
            .setValue(updatedCurrentRoundAnswers)
        Napier.d(
            tag = TAG,
            message = "Answer of player $playerID in round ${currentRound.number} scored with $score points"
        )
    }

    companion object {
        private val TAG = PlayersRepository::class.simpleName
    }
}