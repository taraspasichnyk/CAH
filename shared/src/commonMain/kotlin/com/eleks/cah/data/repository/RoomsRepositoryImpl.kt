package com.eleks.cah.data.repository

import com.eleks.cah.data.extensions.roomOrException
import com.eleks.cah.data.extensions.wordsCount
import com.eleks.cah.data.model.*
import com.eleks.cah.domain.Constants.DB_REF_ANSWERS
import com.eleks.cah.domain.Constants.DB_REF_CURRENT_ROUND
import com.eleks.cah.domain.Constants.DB_REF_PLAYERS
import com.eleks.cah.domain.Constants.DB_REF_ROOMS
import com.eleks.cah.domain.Constants.DEFAULT_PLAYER_CARDS_AMOUNT
import com.eleks.cah.domain.Constants.DEFAULT_ROOM_QUESTION_CARDS
import com.eleks.cah.domain.exceptions.FailedToJoinRoomException
import com.eleks.cah.domain.model.Player
import com.eleks.cah.domain.model.RoomID
import com.eleks.cah.domain.repository.RoomsRepository
import dev.gitlive.firebase.database.DatabaseReference
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlin.random.Random

class RoomsRepositoryImpl(
    databaseReference: DatabaseReference
) : RoomsRepository {

    private val roomsDbReference = databaseReference.child(DB_REF_ROOMS)

    override suspend fun createNewRoom(hostNickname: String): GameRoomDTO {
        val inviteCode = generateInviteCode()
        var newRoom = GameRoomDTO(
            id = inviteCode,
            inviteCode = inviteCode,
            players = emptyMap(),
            questions = generateQuestionCards(),
            answers = generateAnswerCards(),
            currentRound = null
        )
        Napier.d(
            tag = TAG,
            message = "newRoom = $newRoom"
        )
        roomsDbReference.child(inviteCode).setValue(newRoom)
        val gameOwner = addPlayerToRoom(hostNickname, inviteCode, true)
        newRoom = newRoom.copy(players = mapOf(gameOwner.id to gameOwner))
        Napier.d(
            tag = TAG,
            message = "New room created, ID (inviteCode) = $inviteCode, host = $hostNickname"
        )
        return newRoom
    }

    private suspend fun generateInviteCode(): String {
        val allRoomsInviteCodes = roomsDbReference.valueEvents
            .firstOrNull()
            ?.value<HashMap<String, Unit>?>()
            ?.keys

        var newInviteCode: String = Random.nextInt(100000, 999999).toString()
        while (allRoomsInviteCodes?.contains(newInviteCode) == true) {
            newInviteCode = Random.nextInt(100000, 999999).toString()
        }
        return newInviteCode
    }

    private fun generateQuestionCards(): List<QuestionCardDTO> {
        // TODO: Generate question cards
        return listOf(
            "З 2000-го року організація застосовує захоплюючі вигідні засоби відкриттів, зберігання даних та неперервної інтеграції",
            "Завдяки прогресивним та надійним продуктам та послугам, талановитим працівникам і серйозному підходу до інновацій",
            "Мета організації проста: це надання вам глобального громадянства, накопичення та продуктів харчування",
            "Хочемо вражати домашніх улюбленців комфортом новаторства і прагнемо розвивати кабельне телебачення",
            "На постійній основі організація використовує універсальні вигідні засоби зберігання даних",
            "У Матриці до Нео (Кіану Рівз) доходять чутки про Матрицю",
            "Нео проводить ночі за комп'ютером, намагаючись дізнатися секрет Матриці",
            "Нео обирає червону таблетку і прокидається у реальному світі",
            "Після порятунку й одужання на кораблі Морфея, Морфей показує Нео справжню природу Матриці",
            "«Матриця» має відсилання на історичні міфи і філософію",
            "Головному персонажу фільму, Нео, пропонують вибір між червоною і синьою таблеткою.",
        ).mapIndexed { index, sentence ->
            QuestionCardDTO(
                id = index.toString(),
                question = sentence,
                gaps = listOf(Random.nextInt(0, sentence.wordsCount - 1))
            )
        }.sortedBy { Random.nextInt() }.take(DEFAULT_ROOM_QUESTION_CARDS)
    }

    private fun generateAnswerCards(): List<AnswerCardDTO> {
        // TODO: Generate answer cards
        return listOf(
            "на кораблі Морфея",
            "історичні міфи",
            "червону таблетку",
            "Мета організації",
            "захоплюючі вигідні засоби",
            "талановитим працівникам",
            "у реальному світі",
            "синьою таблеткою",
            "персонажу фільму",
            "надійним продуктам",
            "серйозному підходу до інновацій",
            "чутки про Матрицю",
            "глобального громадянства",
            "продуктів харчування",
            "на кораблі Морфея",
            "історичні міфи",
            "червону таблетку",
            "Мета організації",
            "захоплюючі вигідні засоби",
            "талановитим працівникам",
            "у реальному світі",
            "синьою таблеткою",
            "персонажу фільму",
            "надійним продуктам",
            "серйозному підходу до інновацій",
            "чутки про Матрицю",
            "глобального громадянства",
            "продуктів харчування",
            "на кораблі Морфея",
            "історичні міфи",
            "червону таблетку",
            "Мета організації",
            "захоплюючі вигідні засоби",
            "талановитим працівникам",
            "у реальному світі",
            "синьою таблеткою",
            "персонажу фільму",
            "надійним продуктам",
            "серйозному підходу до інновацій",
            "чутки про Матрицю",
            "глобального громадянства",
            "продуктів харчування",
            "на кораблі Морфея",
            "історичні міфи",
            "червону таблетку",
            "Мета організації",
            "захоплюючі вигідні засоби",
            "талановитим працівникам",
            "у реальному світі",
            "синьою таблеткою",
            "персонажу фільму",
            "надійним продуктам",
            "серйозному підходу до інновацій",
            "чутки про Матрицю",
            "глобального громадянства",
            "продуктів харчування",
            "на кораблі Морфея",
            "історичні міфи",
            "червону таблетку",
            "Мета організації",
            "захоплюючі вигідні засоби",
            "талановитим працівникам",
            "у реальному світі",
            "синьою таблеткою",
            "персонажу фільму",
            "надійним продуктам",
            "серйозному підходу до інновацій",
            "чутки про Матрицю",
            "глобального громадянства",
            "продуктів харчування",
            "на кораблі Морфея",
            "історичні міфи",
            "червону таблетку",
            "Мета організації",
            "захоплюючі вигідні засоби",
            "талановитим працівникам",
            "у реальному світі",
            "синьою таблеткою",
            "персонажу фільму",
            "надійним продуктам",
            "серйозному підходу до інновацій",
            "чутки про Матрицю",
            "глобального громадянства",
            "продуктів харчування",
            "на кораблі Морфея",
            "історичні міфи",
            "червону таблетку",
            "Мета організації",
            "захоплюючі вигідні засоби",
            "талановитим працівникам",
            "у реальному світі",
            "синьою таблеткою",
            "персонажу фільму",
            "надійним продуктам",
            "серйозному підходу до інновацій",
            "чутки про Матрицю",
            "глобального громадянства",
            "продуктів харчування",
            "на кораблі Морфея",
            "історичні міфи",
            "червону таблетку",
            "Мета організації",
            "захоплюючі вигідні засоби",
            "талановитим працівникам",
            "у реальному світі",
            "синьою таблеткою",
            "персонажу фільму",
            "надійним продуктам",
            "серйозному підходу до інновацій",
            "чутки про Матрицю",
            "глобального громадянства",
            "продуктів харчування",
            "на кораблі Морфея",
            "історичні міфи",
            "червону таблетку",
            "Мета організації",
            "захоплюючі вигідні засоби",
            "талановитим працівникам",
            "у реальному світі",
            "синьою таблеткою",
            "персонажу фільму",
            "надійним продуктам",
            "серйозному підходу до інновацій",
            "чутки про Матрицю",
            "глобального громадянства",
            "продуктів харчування",
        ).mapIndexed { index, words ->
            AnswerCardDTO(
                id = index.toString(),
                answer = words
            )
        }
    }

    override suspend fun getRoomByIdFlow(roomID: RoomID): Flow<GameRoomDTO> {
        return roomsDbReference.child(roomID)
            .valueEvents
            .map {
                it.value()
            }
    }

    override suspend fun getRoomIfExist(roomID: RoomID): GameRoomDTO? {
        return kotlin.runCatching {
            roomsDbReference.roomOrException(roomID)
        }.getOrNull()
    }

    override suspend fun joinRoom(
        inviteCode: String,
        nickname: String
    ): PlayerDTO {
        roomsDbReference.roomOrException(inviteCode)
        return addPlayerToRoom(nickname, inviteCode, false)
    }

    private suspend fun addPlayerToRoom(
        nickname: String,
        roomId: RoomID,
        gameOwner: Boolean
    ): PlayerDTO {
        return roomsDbReference.child(roomId).child(DB_REF_PLAYERS).push().let { dbRef ->
            val uuid = dbRef.key ?: throw FailedToJoinRoomException(nickname, roomId)
            val newPlayer = PlayerDTO(
                id = uuid,
                nickname = nickname,
                gameOwner = if (gameOwner) 1 else 0,
                cards = emptyList(),
                score = 0,
                state = Player.PlayerState.NOT_READY.toString(),
            )
            dbRef.setValue(newPlayer)
            Napier.d(
                tag = TAG,
                message = "Player $nickname added to room $roomId"
            )
            newPlayer
        }
    }

    override suspend fun startNextRound(roomID: RoomID) {
        roomsDbReference.roomOrException(roomID) {
            val preUpdatedPlayers = savePlayersScores(it)
            refreshPlayersCards(it, preUpdatedPlayers)
            startNextRoundInRoom(it)
            roomsDbReference.roomOrException(roomID) {
                Napier.d("answers = ${it.currentRound?.answers}")
            }
        }
    }

    private fun savePlayersScores(gameRoom: GameRoomDTO): List<PlayerDTO> {
        val gameRoomPlayersList = gameRoom.players.values.toList()
        val currentRound = gameRoom.currentRound ?: return gameRoomPlayersList
        val updatedPlayers = gameRoomPlayersList.map { player ->
            val playerRoundScore = currentRound.answers.firstOrNull {
                it.playerID == player.id
            }?.totalScore ?: return@map player
            player.copy(score = player.score + playerRoundScore)
        }
        Napier.d(
            tag = TAG,
            message = "Players scores in round ${currentRound.number} in room ${gameRoom.id} saved: ${updatedPlayers.joinToString { "${it.nickname} - ${it.score} points" }}"
        )
        return updatedPlayers
    }

    private suspend fun refreshPlayersCards(
        gameRoom: GameRoomDTO,
        preUpdatedPlayers: List<PlayerDTO>
    ) {
        val updatedAnswers = gameRoom.answers.toMutableList()
        Napier.d("upd anwers = ${updatedAnswers.map { it.id }}")

        val updatedPlayers = preUpdatedPlayers.map { player ->
            val updatedPlayerCards = player.cards.toMutableList()
            val newCardsRequired = DEFAULT_PLAYER_CARDS_AMOUNT - updatedPlayerCards.size
            repeat(newCardsRequired) {
                val newCard = updatedAnswers.random()
                updatedPlayerCards.add(newCard)
                updatedAnswers.remove(newCard)
            }
            player.copy(cards = updatedPlayerCards)
        }.associateBy { it.id }

        Napier.d("upd players = ${updatedPlayers}")

        roomsDbReference.child(gameRoom.id).updateChildren(
            mapOf(
                DB_REF_PLAYERS to updatedPlayers,
                DB_REF_ANSWERS to updatedAnswers
            )
        )
        Napier.d(
            tag = TAG,
            message = "Cards in room ${gameRoom.id} updated"
        )
    }

    private suspend fun startNextRoundInRoom(gameRoom: GameRoomDTO) {
        val nextRoundNumber = gameRoom.currentRound?.number?.plus(1) ?: 1
        val nextQuestion = gameRoom.questions.getOrNull(nextRoundNumber - 1)?.id
        if (nextQuestion == null) {
            finishRoom(gameRoom)
            return
        }
        val nextRound = GameRoundDTO(
            id = nextRoundNumber.toString(),
            number = nextRoundNumber,
            question = nextQuestion,
            answers = emptyList()
        )
        roomsDbReference.child(gameRoom.id)
            .child(DB_REF_CURRENT_ROUND)
            .setValue(nextRound)
        Napier.d(
            tag = TAG,
            message = "Room ${gameRoom.id} current round changed to $nextRound"
        )
    }

    private suspend fun finishRoom(gameRoom: GameRoomDTO) {
        roomsDbReference.child(gameRoom.id)
            .child(DB_REF_CURRENT_ROUND)
            .setValue<Unit>(null)//TODO probably removeValue() should be here
        Napier.d(
            tag = TAG,
            message = "Room ${gameRoom.id} finished"
        )
    }

    companion object {
        private val TAG = RoomsRepository::class.simpleName
    }
}