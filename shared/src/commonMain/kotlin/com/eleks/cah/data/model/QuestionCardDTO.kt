package com.eleks.cah.data.model


@kotlinx.serialization.Serializable
data class QuestionCardDTO(
    val id: String = "",
    val question: String = "",
    val gaps: List<Int> = emptyList()
)

