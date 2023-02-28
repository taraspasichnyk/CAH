package com.eleks.cah.data.mapper

import com.eleks.cah.data.model.*
import com.eleks.cah.domain.model.*

fun GameRoomDTO.toModel(): GameRoom {
    val allQuestions = questions.map { it.toModel() }

    return GameRoom(
        id = id,
        inviteCode = inviteCode,
        players = players.values.map { it.toModel() }.sortedBy { !it.gameOwner },
        questions = allQuestions,
        answers = answers.map { it.toModel() },
        currentRound = currentRound?.toModel(allQuestions),
    )
}

fun PlayerDTO.toModel() = Player(
    id = id,
    nickname = nickname,
    gameOwner = gameOwner == 1L,
    cards = cards.map { it.toModel() },
    score = score,
    state = Player.PlayerState.valueOf(state),
)

fun QuestionCardDTO.toModel() = QuestionCard(
    id = id,
    question = question,
    gaps = gaps,
)

fun AnswerCardDTO.toModel() = AnswerCard(
    id = id,
    answer = answer,
)

fun GameRoundDTO.toModel(
    allQuestions: List<QuestionCard>,
) = GameRound(
    id = id,
    number = number,
    masterCard = allQuestions.first { it.id == question },
    playerCards = answers.map { it.toModel() },
    state = GameRound.GameRoundState.valueOf(state),
)

fun RoundPlayerAnswerDTO.toModel(): RoundPlayerAnswer {
    return RoundPlayerAnswer(
        playerID = playerID,
        playerAnswers = playerAnswers,
        score = totalScore
    )
}