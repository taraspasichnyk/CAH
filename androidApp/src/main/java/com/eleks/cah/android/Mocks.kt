package com.eleks.cah.android

import com.eleks.cah.domain.model.*

fun mockedQuestionCard() = QuestionCard("0", "Question with missing word", listOf(2))

fun mockedRound() = GameRound(
    "0",
    1,
    mockedQuestionCard(),
    playerCards = emptyList(),
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

fun mockedRoundPlayerAnswers() = listOf<RoundPlayerAnswer>(
    RoundPlayerAnswer("0", listOf("0"), 0),
    RoundPlayerAnswer("1", listOf("1"), 0),
    RoundPlayerAnswer("2", listOf("2"), 0),
    RoundPlayerAnswer("3", listOf("3"), 0),
)