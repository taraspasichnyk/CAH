package com.eleks.cah.domain.usecase.player_state

import com.eleks.cah.domain.model.Player
import com.eleks.cah.domain.model.PlayerID
import com.eleks.cah.domain.model.RoomID
import com.eleks.cah.domain.repository.PlayersRepository

class UpdatePlayerStateUseCaseImpl(
    private val playersRepository: PlayersRepository
): UpdatePlayerStateUseCase {
    override suspend fun invoke(
        roomID: RoomID,
        playerID: PlayerID,
        newState: Player.PlayerState
    ) {
        playersRepository.updatePlayerState(
            roomID,
            playerID,
            newState
        )
    }
}