package com.eleks.cah.data.mapper

import com.eleks.cah.data.model.*
import com.eleks.cah.domain.model.*
import io.github.aakira.napier.Napier

fun GameRoomDTO.toModel(): GameRoom {
    val availableQuestions = questions.map { it.toModel() }
    val allAnswers = answers.map { it.toModel() }

    return GameRoom(
        id = id,
        inviteCode = inviteCode,
        players = players.values.map { it.toModel() }.sortedBy { !it.isGameOwner },
        questions = availableQuestions,
        answers = allAnswers,
        currentRound = currentRound?.toModel(availableQuestions, allAnswers),
    )
}

fun PlayerDTO.toModel() = Player(
    id = id,
    nickname = nickname,
    isGameOwner = gameOwner == 1L,
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
    isUsed = used == 1L
)

fun GameRoundDTO.toModel(
    allQuestions: List<QuestionCard>,
    allAnswers: List<AnswerCard>
): GameRound {
    Napier.d(tag = "###", message = "$state")
    return GameRound(
        id = id,
        number = number,
        masterCard = allQuestions.first { it.id == question },
        answers = answers.map { it.toModel(allAnswers) },
        state = GameRound.GameRoundState.valueOf(state),
    )
}

fun RoundPlayerAnswerDTO.toModel(allAnswers: List<AnswerCard>): RoundPlayerAnswer {

    return RoundPlayerAnswer(
        playerID = playerID,
        playerAnswers = playerAnswers
            .map { pa -> allAnswers.first { it.id == pa } },
        score = totalScore
    )
}