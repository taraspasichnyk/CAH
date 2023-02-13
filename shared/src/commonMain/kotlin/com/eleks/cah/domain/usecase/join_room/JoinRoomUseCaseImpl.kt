package com.eleks.cah.domain.usecase.join_room

import com.eleks.cah.domain.repository.RoomsRepository

class JoinRoomUseCaseImpl(
    private val roomsRepository: RoomsRepository
) : JoinRoomUseCase {
    override suspend fun invoke(
        inviteCode: String,
        nickname: String
    ) {
        roomsRepository.joinRoom(inviteCode, nickname)
    }
}