package com.eleks.cah.domain.usecase.answer

import com.eleks.cah.domain.model.AnswerCardID
import com.eleks.cah.domain.model.PlayerID
import com.eleks.cah.domain.model.RoomID

interface AnswerUseCase {
    suspend fun invoke(
        roomID: RoomID,
        playerID: PlayerID,
        playerAnswers: List<AnswerCardID>
    )
}