package com.eleks.cah.domain.usecase.room

import com.eleks.cah.data.mapper.toModel
import com.eleks.cah.domain.model.GameRoom
import com.eleks.cah.domain.model.RoomID
import com.eleks.cah.domain.repository.RoomsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class GetRoomUseCaseImpl(
    private val roomsRepository: RoomsRepository
): GetRoomUseCase {
    override suspend fun invoke(
        roomID: RoomID,
        scope: CoroutineScope
    ): StateFlow<GameRoom?> {
        return roomsRepository.getRoomByIdFlow(roomID)
            .map { it.toModel() }
            .stateIn(scope)
    }
}