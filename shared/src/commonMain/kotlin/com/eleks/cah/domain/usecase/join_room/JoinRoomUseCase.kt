package com.eleks.cah.domain.usecase.join_room

import com.eleks.cah.domain.model.Player

interface JoinRoomUseCase {
    suspend operator fun invoke(
        inviteCode: String,
        nickname: String
    ): Player
}