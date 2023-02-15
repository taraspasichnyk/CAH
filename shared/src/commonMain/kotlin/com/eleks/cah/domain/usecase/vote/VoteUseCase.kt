package com.eleks.cah.domain.usecase.vote

import com.eleks.cah.domain.model.PlayerID
import com.eleks.cah.domain.model.RoomID

interface VoteUseCase {
    suspend fun invoke(
        roomID: RoomID,
        playerID: PlayerID,
        score: Int
    )
}