package com.eleks.cah.domain.usecase.next_round

import com.eleks.cah.domain.model.RoomID

interface StartNextRoundUseCase {
    suspend operator fun invoke(roomID: RoomID)
}