package com.eleks.cah.domain.model

data class GameRoom(
    val id: RoomID,
    val inviteCode: String,
    val players: List<Player>,
    val questions: List<QuestionCard>,
    val answers: List<AnswerCard>,
    val currentRound: GameRound?
)