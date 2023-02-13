package com.eleks.cah.domain.usecase.vote

import com.eleks.cah.domain.model.PlayerID
import com.eleks.cah.domain.model.RoomID
import com.eleks.cah.domain.repository.PlayersRepository

class VoteUseCaseImpl(
    private val playersRepository: PlayersRepository
): VoteUseCase {
    override suspend fun invoke(
        roomID: RoomID,
        playerID: PlayerID,
        score: Int
    ) {
        playersRepository.voteForAnswer(
            roomID,
            playerID,
            score
        )
    }
}