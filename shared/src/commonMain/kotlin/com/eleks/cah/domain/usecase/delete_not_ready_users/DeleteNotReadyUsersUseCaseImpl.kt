package com.eleks.cah.domain.usecase.delete_not_ready_users

import com.eleks.cah.domain.model.RoomID
import com.eleks.cah.domain.repository.PlayersRepository

class DeleteNotReadyUsersUseCaseImpl(
    private val playersRepository: PlayersRepository
) : DeleteNotReadyUsersUseCase {
    override suspend fun invoke(
        roomID: RoomID
    ) {
        playersRepository.deleteNotReadyUsers(roomID)
    }
}