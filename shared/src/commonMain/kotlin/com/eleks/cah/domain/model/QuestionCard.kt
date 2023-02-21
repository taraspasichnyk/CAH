package com.eleks.cah.domain.model

import io.github.aakira.napier.Napier

data class QuestionCard(
    val id: QuestionCardID,
    val question: String,
    val gaps: List<Int>
) {
    val text: String
        get() {
            Napier.d("QCARD: $question")
            Napier.d("QCARD: $gaps")
            return buildString {
                val words = question.split(" ")
                words.forEachIndexed { index, s ->
                    if(index in gaps){
                        append(" ___ ")
                    } else{
                        append(" $s ")
                    }
                }
            }
        }
}

