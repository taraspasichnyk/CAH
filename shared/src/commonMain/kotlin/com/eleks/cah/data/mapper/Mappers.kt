package com.eleks.cah.data.mapper

import com.eleks.cah.data.model.*
import com.eleks.cah.domain.model.*
import io.github.aakira.napier.Napier

fun GameRoomDTO.toModel(): GameRoom {
    val allQuestions = questions.map { it.toModel() }
    val allAnswers = answers.map { it.toModel() }

    return GameRoom(
        id = id,
        inviteCode = inviteCode,
        players = players.values.map { it.toModel() }.sortedBy { !it.gameOwner },
        questions = allQuestions,
        answers = answers.map { it.toModel() },
        currentRound = currentRound?.toModel(allQuestions, allAnswers),
    )
}

fun PlayerDTO.toModel() = Player(
    id = id,
    nickname = nickname,
    gameOwner = gameOwner,
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
    allAnswers: List<AnswerCard>,
) = GameRound(
    id = id,
    number = number,
    question = allQuestions.first { it.id == question },
    answers = answers.map { it.toModel(allAnswers) },
    timer = timer,
    state = GameRound.GameRoundState.valueOf(state),
)

fun RoundPlayerAnswerDTO.toModel(allAnswers: List<AnswerCard>): RoundPlayerAnswer {
    Napier.d("ap = $playerAnswers")
    Napier.d("aa = ${allAnswers.map { it.id }}")
    return RoundPlayerAnswer(
        playerID = playerID,
        playerAnswers = playerAnswers.map { id -> allAnswers.first { it.id == id } },
        score = score,
    )
}