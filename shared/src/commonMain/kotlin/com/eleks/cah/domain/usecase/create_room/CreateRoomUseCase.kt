package com.eleks.cah.domain.usecase.create_room

import com.eleks.cah.domain.model.GameRoom

interface CreateRoomUseCase {
    suspend operator fun invoke(hostNickname: String): GameRoom
}