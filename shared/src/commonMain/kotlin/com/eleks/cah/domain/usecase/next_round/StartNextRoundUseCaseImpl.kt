package com.eleks.cah.domain.usecase.next_round

import com.eleks.cah.domain.model.RoomID
import com.eleks.cah.domain.repository.RoomsRepository

class StartNextRoundUseCaseImpl(
    private val roomsRepository: RoomsRepository,
): StartNextRoundUseCase {
    override suspend fun invoke(roomID: RoomID) {
        roomsRepository.startNextRound(roomID)
    }
}