package com.eleks.cah.data.model

@kotlinx.serialization.Serializable
data class GameRoundDTO(
    val id: String = "",
    val number: Int = -1,
    val question: String = "",
    val answers: List<RoundPlayerAnswerDTO> = emptyList(),
    val state: String = ""
)

