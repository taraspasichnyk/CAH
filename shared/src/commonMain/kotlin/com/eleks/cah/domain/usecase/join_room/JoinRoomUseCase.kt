package com.eleks.cah.domain.usecase.join_room

interface JoinRoomUseCase {
    suspend fun invoke(
        inviteCode: String,
        nickname: String
    )
}