package com.eleks.cah.data.repository

import com.eleks.cah.data.extensions.roomOrException
import com.eleks.cah.data.model.GameRoomDTO
import com.eleks.cah.data.model.PlayerDTO
import com.eleks.cah.data.model.RoundPlayerAnswerDTO
import com.eleks.cah.domain.Constants.DB_REF_ANSWERS
import com.eleks.cah.domain.Constants.DB_REF_CURRENT_ROUND
import com.eleks.cah.domain.Constants.DB_REF_PLAYERS
import com.eleks.cah.domain.Constants.DB_REF_ROOMS
import com.eleks.cah.domain.Constants.DB_REF_STATE
import com.eleks.cah.domain.exceptions.PlayerNotFoundException
import com.eleks.cah.domain.exceptions.RoomNoCurrentRoundException
import com.eleks.cah.domain.model.GameRound
import com.eleks.cah.domain.model.Player
import com.eleks.cah.domain.model.PlayerID
import com.eleks.cah.domain.model.RoomID
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
        val prevAnswers = currentRound.answers.toMutableList()
        //delete prev player answer if exists
        prevAnswers.removeAll { it.playerID == playerID }

        val answers = prevAnswers + playerAnswer
        val isLastAnswer = gameRoomDto.players.keys.size == answers.size
        val updatedCurrentRound = currentRound.copy(
            answers = answers,
            state = if (isLastAnswer) {
                GameRound.GameRoundState.VOTING.name
            } else {
                currentRound.state
            }
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
        playerID: PlayerID,
        score: Int
    ) {
        roomsDbReference.roomOrException(roomID) {
            updateAnswerScore(it, playerID, score)
        }
    }

    private suspend fun updateAnswerScore(
        gameRoomDto: GameRoomDTO,
        playerID: PlayerID,
        score: Int
    ) {
        val currentRound =
            gameRoomDto.currentRound ?: throw RoomNoCurrentRoundException(gameRoomDto.id)
        val updatedCurrentRoundAnswers = currentRound.answers.map {

            if (it.playerID == playerID) {
                it.copy(score = it.score + score)
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

    override suspend fun deleteNotReadyUsers(roomID: RoomID) {
        val notReadyPlayerIds = roomsDbReference.child(roomID)
            .child(DB_REF_PLAYERS)
            .valueEvents
            .firstOrNull()
            ?.value<HashMap<String, PlayerDTO>?>()
            ?.values.orEmpty()
            .filter { it.state != Player.PlayerState.READY.name }
            .map { it.id }
        Napier.d(
            tag = TAG,
            message = "Deleted users $notReadyPlayerIds"
        )
        if (notReadyPlayerIds.isNotEmpty()) {
            val players = roomsDbReference.child(roomID)
                .child(DB_REF_PLAYERS)
            notReadyPlayerIds.forEach {
                players.child(it)
                    .removeValue()
            }
        }
    }

    companion object {
        private val TAG = PlayersRepository::class.simpleName
    }
}