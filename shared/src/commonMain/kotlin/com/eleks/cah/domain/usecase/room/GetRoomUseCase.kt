package com.eleks.cah.domain.usecase.room

import com.eleks.cah.domain.model.GameRoom
import com.eleks.cah.domain.model.RoomID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface GetRoomUseCase {
    suspend operator fun invoke(
        roomID: RoomID,
        scope: CoroutineScope
    ): StateFlow<GameRoom?>
}