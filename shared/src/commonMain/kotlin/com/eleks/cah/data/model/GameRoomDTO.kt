package com.eleks.cah.data.model

@kotlinx.serialization.Serializable
data class GameRoomDTO(
    val id: String = "",
    val inviteCode: String = "",
    val players: Map<String, PlayerDTO> = emptyMap(),
    val questions: List<QuestionCardDTO> = emptyList(),
    val answers: List<AnswerCardDTO> = emptyList(),
    val currentRound: GameRoundDTO? = null
)

