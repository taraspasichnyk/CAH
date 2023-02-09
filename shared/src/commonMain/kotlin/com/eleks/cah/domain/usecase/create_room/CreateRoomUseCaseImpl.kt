package com.eleks.cah.domain.usecase.create_room

import com.eleks.cah.data.mapper.toModel
import com.eleks.cah.domain.model.GameRoom
import com.eleks.cah.domain.repository.RoomsRepository

class CreateRoomUseCaseImpl(
    private val roomsRepository: RoomsRepository
): CreateRoomUseCase {
    override suspend fun invoke(
        hostNickname: String
    ): GameRoom = roomsRepository.createNewRoom(hostNickname).toModel()
}