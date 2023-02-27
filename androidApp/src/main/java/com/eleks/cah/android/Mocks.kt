package com.eleks.cah.android

import com.eleks.cah.domain.model.*

fun mockedQuestionCard() = QuestionCard("0", "Question with missing word", listOf(2))

fun mockedAnswerCards() = listOf(
    AnswerCard("0", "Answer 1"),
    AnswerCard("1", "Answer 2"),
    AnswerCard("2", "Answer 3"),
    AnswerCard("3", "Answer 4")
)


fun mockedRound() = GameRound(
    "0",
    1,
    mockedQuestionCard(),
    playerCards = emptyList(),
    0,
    GameRound.GameRoundState.ACTIVE
)

fun mockedPlayer() = Player(
    "1",
    "Andrii",
    true,
    listOf(
        AnswerCard("0", "bread"),
        AnswerCard("1", "sad"),
        AnswerCard("2", "bad"),
        AnswerCard("3", "missing"),
    ),
    0,
    Player.PlayerState.ANSWERING
)

fun mockedRoundPlayerAnswers(): List<RoundPlayerAnswer> {
    val playerAnswers = mockedAnswerCards()
    return listOf<RoundPlayerAnswer>(
        RoundPlayerAnswer("0", playerAnswers, 0),
        RoundPlayerAnswer("1", playerAnswers, 1),
        RoundPlayerAnswer("2", playerAnswers, 2),
        RoundPlayerAnswer("3", playerAnswers, 3),
    )
}