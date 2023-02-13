package com.eleks.cah.domain.usecase.player_state

import com.eleks.cah.domain.model.Player
import com.eleks.cah.domain.model.PlayerID
import com.eleks.cah.domain.model.RoomID

interface UpdatePlayerStateUseCase {
    suspend fun invoke(
        roomID: RoomID,
        playerID: PlayerID,
        newState: Player.PlayerState
    )
}