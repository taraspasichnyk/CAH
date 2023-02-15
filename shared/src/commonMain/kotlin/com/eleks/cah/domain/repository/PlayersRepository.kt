package com.eleks.cah.domain.repository

import com.eleks.cah.domain.model.PlayerID
import com.eleks.cah.domain.model.RoomID
import com.eleks.cah.domain.model.Player

interface PlayersRepository {
    suspend fun updatePlayerState(
        roomID: RoomID,
        playerID: PlayerID,
        newState: Player.PlayerState
    )

    suspend fun makeAnswer(
        roomID: RoomID,
        playerID: PlayerID,
        playerAnswers: List<String>
    )

    suspend fun voteForAnswer(
        roomID: RoomID,
        playerID: PlayerID,
        score: Int
    )
}