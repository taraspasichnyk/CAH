package com.eleks.cah.domain.usecase.vote

import com.eleks.cah.domain.model.PlayerID
import com.eleks.cah.domain.model.RoomID

interface VoteUseCase {
    suspend operator fun invoke(
        roomID: RoomID,
        voterID: PlayerID,
        playerID: PlayerID,
        score: Int
    )
}