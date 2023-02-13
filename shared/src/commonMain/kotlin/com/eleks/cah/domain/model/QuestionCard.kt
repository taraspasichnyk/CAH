package com.eleks.cah.domain.model

data class QuestionCard(
    val id: QuestionCardID,
    val question: String,
    val gaps: List<Int>
)

