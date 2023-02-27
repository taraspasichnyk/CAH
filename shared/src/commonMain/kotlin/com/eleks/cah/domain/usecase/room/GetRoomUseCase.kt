package com.eleks.cah.domain.usecase.room

import com.eleks.cah.domain.model.GameRoom
import com.eleks.cah.domain.model.RoomID
import kotlinx.coroutines.flow.Flow

interface GetRoomUseCase {
    operator fun invoke(
        roomID: RoomID
    ): Flow<GameRoom?>
}