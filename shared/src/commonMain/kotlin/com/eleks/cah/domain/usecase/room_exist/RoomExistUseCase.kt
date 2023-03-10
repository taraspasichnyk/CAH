package com.eleks.cah.domain.usecase.room_exist

import com.eleks.cah.domain.model.RoomID

interface RoomExistUseCase {
    suspend operator fun invoke(
        roomID: RoomID
    ): Boolean
}