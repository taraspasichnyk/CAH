package com.eleks.cah.domain.usecase.answer

import com.eleks.cah.domain.model.RoomID
import com.eleks.cah.domain.model.AnswerCardID
import com.eleks.cah.domain.model.PlayerID
import com.eleks.cah.domain.repository.PlayersRepository

class AnswerUseCaseImpl(
    private val playersRepository: PlayersRepository
): AnswerUseCase {
    override suspend fun invoke(
        roomID: RoomID,
        playerID: PlayerID,
        playerAnswers: List<AnswerCardID>
    ) {
        playersRepository.makeAnswer(
            roomID,
            playerID,
            playerAnswers
        )
    }
}