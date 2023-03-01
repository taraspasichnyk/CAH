package com.eleks.cah.data.model

@kotlinx.serialization.Serializable
data class AnswerCardDTO(
    val id: String = "",
    val answer: String = "",
    val used: Long = 0L
)

