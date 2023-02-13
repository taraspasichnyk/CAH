package com.eleks.cah.data.model

@kotlinx.serialization.Serializable
data class RoundPlayerAnswerDTO(
    val playerID: String = "",
    val playerAnswers: List<String> = emptyList(),
    val score: Int = 0
)

