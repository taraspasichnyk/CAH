package com.eleks.cah.domain.usecase.room

import com.eleks.cah.data.mapper.toModel
import com.eleks.cah.domain.model.GameRoom
import com.eleks.cah.domain.model.RoomID
import com.eleks.cah.domain.repository.RoomsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetRoomUseCaseImpl(
    private val roomsRepository: RoomsRepository
) : GetRoomUseCase {
    override fun invoke(
        roomID: RoomID
    ): Flow<GameRoom?> {
        return roomsRepository.getRoomByIdFlow(roomID)
            .map { it.toModel() }

    }
}