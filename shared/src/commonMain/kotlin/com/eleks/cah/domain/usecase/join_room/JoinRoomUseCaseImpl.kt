package com.eleks.cah.domain.usecase.join_room

import com.eleks.cah.data.mapper.toModel
import com.eleks.cah.domain.model.Player
import com.eleks.cah.domain.repository.RoomsRepository

class JoinRoomUseCaseImpl(
    private val roomsRepository: RoomsRepository
) : JoinRoomUseCase {
    override suspend fun invoke(
        inviteCode: String,
        nickname: String
    ): Player {
        return roomsRepository.joinRoom(inviteCode, nickname).toModel()
    }
}