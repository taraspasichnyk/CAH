package com.eleks.cah.data.model

@kotlinx.serialization.Serializable
data class PlayerDTO(
    val id: String = "",
    val nickname: String = "",
    val gameOwner: Long = 0,
    val cards: List<AnswerCardDTO> = emptyList(),
    val score: Int = 0,
    val state: String = ""
)

