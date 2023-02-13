package com.eleks.cah.domain.usecase.next_round

import com.eleks.cah.domain.model.RoomID

interface StartNextRoundUseCase {
    suspend fun invoke(roomID: RoomID)
}