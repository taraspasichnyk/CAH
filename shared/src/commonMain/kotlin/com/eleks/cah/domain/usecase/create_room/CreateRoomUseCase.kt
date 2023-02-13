package com.eleks.cah.domain.usecase.create_room

import com.eleks.cah.domain.model.GameRoom

interface CreateRoomUseCase {
    suspend fun invoke(hostNickname: String): GameRoom
}