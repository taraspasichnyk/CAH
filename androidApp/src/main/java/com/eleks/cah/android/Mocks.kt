package com.eleks.cah.android

import com.eleks.cah.domain.model.*

fun mockedGameRoom() = GameRoom(
    id = "100500",
    inviteCode = "100500",
    players = listOf(
        mockedPlayer(score = 25),
        mockedPlayer(score = 31),
        mockedPlayer(score = 23),
        mockedPlayer(score = 15),
        mockedPlayer(score = 19),
        mockedPlayer(score = 10),
        mockedPlayer(score = 8),
        mockedPlayer(score = 33),
        mockedPlayer(score = 27),
        mockedPlayer(score = 13),
    ),
    questions = listOf(
        mockedQuestionCard(id = "0"),
        mockedQuestionCard(id = "1"),
        mockedQuestionCard(id = "2"),
        mockedQuestionCard(id = "3"),
        mockedQuestionCard(id = "4"),
    ),
    answers = listOf(
        mockedAnswerCard(id = "0"),
        mockedAnswerCard(id = "1"),
        mockedAnswerCard(id = "2"),
        mockedAnswerCard(id = "3"),
        mockedAnswerCard(id = "4"),
    ),
    currentRound = mockedRound(),
)

fun mockedQuestionCard(
    id: String = "0"
) = QuestionCard(id, "Question with missing word", listOf(2))

fun mockedAnswerCard(
    id: String = "0"
) = AnswerCard(id, "Answer")

fun mockedRound() = GameRound(
    "0",
    1,
    mockedQuestionCard(),
    answers = emptyList(),
    GameRound.GameRoundState.ACTIVE
)

fun mockedPlayer(
    score: Int = 0
) = Player(
    "1",
    "Andrii",
    true,
    listOf(
        AnswerCard("0", "bread"),
        AnswerCard("1", "sad"),
        AnswerCard("2", "bad"),
        AnswerCard("3", "missing"),
    ),
    score,
    Player.PlayerState.ANSWERING
)

fun mockedRoundPlayerAnswers() = listOf<RoundPlayerAnswer>(
    RoundPlayerAnswer("0", listOf("0"), 0),
    RoundPlayerAnswer("1", listOf("1"), 0),
    RoundPlayerAnswer("2", listOf("2"), 0),
    RoundPlayerAnswer("3", listOf("3"), 0),
)