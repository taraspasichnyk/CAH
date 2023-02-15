package com.eleks.cah.domain.usecase.room_exist

import com.eleks.cah.domain.model.RoomID
import com.eleks.cah.domain.repository.RoomsRepository

class RoomExistUseCaseImpl(
    private val roomsRepository: RoomsRepository
) : RoomExistUseCase {
    override suspend fun invoke(
        roomID: RoomID
    ): Boolean {
        return roomsRepository.getRoomIfExist(roomID) != null
    }
}