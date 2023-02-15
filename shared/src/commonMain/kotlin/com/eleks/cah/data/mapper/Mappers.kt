package com.eleks.cah.data.mapper

import com.eleks.cah.data.model.*
import com.eleks.cah.domain.model.*

fun GameRoomDTO.toModel() = GameRoom(
    id = id,
    inviteCode = inviteCode,
    players = players.values.map { it.toModel() }.sortedBy { !it.gameOwner },
    questions = questions.map { it.toModel() },
    answers = answers.map { it.toModel() },
    currentRound = currentRound?.toModel(),
)

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

fun GameRoundDTO.toModel() = GameRound(
    id = id,
    number = number,
    question = question,
    answers = answers.map { it.toModel() },
    timer = timer,
    state = GameRound.GameRoundState.valueOf(state),
)

fun RoundPlayerAnswerDTO.toModel() = RoundPlayerAnswer(
    playerID = playerID,
    playerAnswers = playerAnswers,
    score = score,
)