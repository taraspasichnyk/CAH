package com.eleks.cah.data.model

@kotlinx.serialization.Serializable
data class RoundPlayerAnswerDTO(
    val playerID: String = "",
    val playerAnswers: List<String> = emptyList(),
    val scores: Map<String, Int> = emptyMap()
) {
    val totalScore: Int
        get() = scores.values.sum()
}

