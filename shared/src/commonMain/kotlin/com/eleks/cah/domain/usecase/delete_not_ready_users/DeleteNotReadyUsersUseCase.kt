package com.eleks.cah.domain.usecase.delete_not_ready_users

import com.eleks.cah.domain.model.RoomID

interface DeleteNotReadyUsersUseCase {
    suspend operator fun invoke(roomID: RoomID)
}