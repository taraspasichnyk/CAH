package com.eleks.cah.data.repository

import com.eleks.cah.data.extensions.roomOrException
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
            "Я ніколи насправді не розумів програмування, доки не зіткнувся з ____ .",
            "Вітаємо! Ви були обрані для нашої літньої інтернатури. Хоча ми не можемо пропонувати зарплату, ми можемо пропонувати \"вам\" ____ .",
            "Романтичний, незабутній вечір був б неповним без ____ .",
            "Скасувати всі мої зустрічі. У нас є ситуація з ____ , яка вимагає моєї негайної уваги.",
            "Всі на землю! Ми не хочемо нікому шкодити. Ми тут лише для ____ .",
            "Нічого не каже \"Я тебе люблю\" як ____ .",
            "Той, хто контролює ____ , контролює світ.",
            "ЦРУ тепер допитує ворогів, постійно піддаючи їх ____ .",
            "Попередження Календаря Google: ____ за 10 хвилин.",
            "У новому DLC для Mass Effect Шепард повинен врятувати галактику від ____ .",
            "Друже, це дурниця. ____ .",
            "Легенда говорить про принцесу, яка дрімає вже тисячу років і може бути пробуджена лише ____ .",
            "Після відключки на Новий Рік, я прокинувся від ____ .",
            "Знаєш, хто ще любив ____ ? Гітлер.",
            "Цей додаток в основному Tinder, але для ____ .",
            "У мене строга політика. Перше побачення, вечеря. Друге, поцілунок. Третє, ____ .",
            "Наша компанія має тверду позицію щодо ____ .",
            "Замість моєї душі Диявол обіцяв мені ____ .",
            "Гроші не можуть купити мені любов, але вони можуть купити мені ____ .",
            "Цей місяць Космо: “Покращіть своє сексуальне життя за допомогою ____ у спальні”.",
            "Вона просто один з хлопців, знаєш? Вона любить пиво, футбол і ____ .",
            "Друже, ця марихуана неймовірна! Це сорт ____ .",
            "Тут, в Академії для Талановитих Дітей, ми даємо студентам можливість досліджувати ____ за своїм ритмом.",
            "Моє ім'я Ініго Монтойя. Ви вбили мого батька. Готуйтеся до ____ .",
            "Я дуже сподіваюся, що моя бабуся не попросить мене знову пояснити ____ .",
            "____ : Achievement unlocked.",
            "Я просто залишуся дома сьогодні вечеря. Ти знаєш, Нетфлікс і ____ .",
            "Благословенний ти, Господь наш Боже, творець усього світу, хто дав нам ____ .",
            "____ : Перевірено дітьми, одобрено матерями.",
            "В дитинстві, Сальвадор Далі намалював десятки картин ____ .",
            "Наші відносини строго професійні. Не заплутаймо речей ____ .",
            "Бейба, повернись до мене додому і я покажу тобі ____ .",
            "Найгірший день НА СВІТІ. #____ .",
            "Чому завершились мої останні стосунки? ____ .",
            "Я зрозумів важким шляхом, що не можна розвеселити горюючого друга ____ .",
            "Мені снився жахливий сон, батьку. Я бачив, як гори рушаться, зірки падають з неба. Я бачив ____ .",
            "Під час моєї першої гри в D&D я несподівано прикликав ____ .",
            "Мені дуже подобається ваша презентація, можете додати ще ____ ?",
            "Ніколи не шукайте в Google ____ .",
            "Астрономи виявили, що у всесвіті є 5% звичайної матерії, 25% темної матерії і 70% ____ .",
            "Я не вірю в Бога. Я вірю у ____ .",
            "Що я приніс з Івано-Франківська? ____ .",
            "Це прекрасний період мого життя. Я молодий, гарячий і повний ____ .",
            "Що за звук? ____ .",
            "Чому курка перетинала дорогу? ____ .",
            "Що могло би здатись бабусі огидним, але дивно приваблювати її? ____ .",
            "Прибирання! ____ ?",
            "Ну, бляха. Мої очі не такі добрі, але я з‘їм свій кросівок, якщо це не ____ !",
            "Що привело до зупинки оргії? ____ .",
            "Мій дідусь працював з нуля. Коли він прийшов до цієї країни, у нього було лише взуття на ногах і ____ .",
        ).mapIndexed { index, sentence ->
            val wordsList = sentence.split(" ")
            val indexOfGap = wordsList.indexOfFirst { it.contains("____") }
            QuestionCardDTO(
                id = index.toString(),
                question = sentence,
                gaps = listOf(indexOfGap)
            )
        }.sortedBy { Random.nextInt() }.take(DEFAULT_ROOM_QUESTION_CARDS)
    }

    private fun generateAnswerCards(): List<AnswerCardDTO> {
        // TODO: Generate answer cards
        return listOf(
            "Німецька порнографія підземель.",
            "Літаючі сексуальні змії.",
            "Руки Мішель Обами.",
            "Білі люди.",
            "Так злитися, що попасти в вістря.",
            "Tasteful sideboob.",
            "Молитися, щоб відійти від геїв.",
            "Два карлики кашляють в відро.",
            "MechaHitler.",
            "Бути матерієським чарівником.",
            "Розчарування на день народження.",
            "Щенячі!",
            "Вітроміль повний трупів.",
            "Хлопці, які не дзвонять.",
            "Расистські питання на САТ.",
            "Помирання.",
            "Steven Hawking говорить непристойно.",
            "Бути в полум'ї.",
            "Життя печалі.",
            "Ерекція, яка триває більше ніж чотири години.",
            "AIDS",
            "Самополіпне хокеї на льоді.",
            "Glenn Beck зламавши своє скротум.",
            "Відкриття.",
            "Яйця птеродактиля.",
            "Непереносна борг.",
            "Евгеніка.",
            "Обмін ласкавими словами.",
            "Помирання від дизентерії.",
            "Roofies.",
            "Роздягання і перегляд Нікельбека.",
            "Заборонений плід.",
            "Республіканці.",
            "Великий вибух.",
            "Анальні білизни.",
            "Однокінцівки.",
            "Люди.",
            "Несподіваний секс!",
            "Кім Чен Ір.",
            "Приховування вістря.",
            "Агрономія.",
            "Glenn Beck переслідується гусарами.",
            "Зробити пискляву особистість.",
            "Солодка нагадування.",
            "Євреї.",
            "Харизма.",
            "ТОБІ ПОТРІБНО ПОБУДУВАТИ ДОДАТКОВІ ПІЛОНИ.",
            "Секс з пандою.",
            "Знімання своєї рубашки.",
            "Drive-by стрільба.",
            "Рональд Рейган.",
            "Голос Моргана Фрімена.",
            "Вибух в пісню і танець.",
            "Єврейські фратерніті.",
            "Мертві діти.",
            "Самостійне заняття.",
            "Виняткові препарати.",
            "Все, що можна сісти за 4,99 доларів.",
            "Інцест.",
            "Скальпінг.",
            "Суп, який занадто гарячий.",
            "Уберменщина.",
            "Нацисти.",
            "Том Круз.",
            "Затишки при згадуванні Хутів і Тутсів.",
            "Смачні підгузники.",
            "Веселка.",
            "Раціональне планування подій.",
            "Дитинство.",
            "Проставки від червоної кари.",
            "Кліторис.",
            "Джон Уілкс Бут.",
            "Складне стекло.",
            "Сара Пейлін.",
            "Сексуальні пілотки подушок.",
            "Дрожжі.",
            "Повна передня голинка.",
            "Партія погано настроєних.",
            "Американська мрія.",
            "Дитячі красуня конкурси.",
            "Підлітковість.",
            "Торсіонна торсія тестікула.",
            "Незрозуміла дурність.",
            "Притискання.",
            "Атитуда.",
            "Придатні трансвестити.",
            "Партійні порушники.",
            "Американська мрія.",
            "Дитячі красуня конкурси.",
            "Підлітковість.",
            "Торсіонна торсія тестікула.",
            "Незрозуміла дурність.",
            "Притискання.",
            "Атитуда.",
            "Придатні трансвестити.",
            "Партійні порушники.",
            "Дідько.",
            "Не зацікавлення в Третьому світі.",
            "Лизання речей, щоб захопити їх як свої.",
            "Чингіс Хан.",
            "Працьовитий мексиканець.",
            "RoboCop.",
            "Мій статус відносин.",
            "Чищення під загинами.",
            "Порно зірки.",
            "Конєва м'ясо.",
            "Сонце і дужки.",
            "Очікування подріжки і блювання на підлогу.",
            "Барак Обама",
            "Степан Гіга",
            "Знімати персики з дерева біля ЖЕКу",
            "Місити палкою кропиву",
            "Неймовірний покемон Сквіртл",
            "Картонний пакет Кагору",
            "Футбольний клуб \"Карпати\"",
            "Майнити біткойни на Atari",
            "Стрілецька Дивізія \"СС Галичина\"",
            "Божеволіти він нестримного програмування",
            "Тім лід гомосексуаліст",
        ).mapIndexed { index, words ->
            AnswerCardDTO(
                id = index.toString(),
                answer = words
            )
        }
    }

    override fun getRoomByIdFlow(roomID: RoomID): Flow<GameRoomDTO> {
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
            refreshPlayers(it, preUpdatedPlayers)
            startNextRoundInRoom(it)
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

    private suspend fun refreshPlayers(
        gameRoom: GameRoomDTO,
        preUpdatedPlayers: List<PlayerDTO>
    ) {
        val deckAnswers = gameRoom.answers.toMutableList()

        val updatedPlayers = preUpdatedPlayers.map { player ->
            val updatedPlayerCards = player.cards.toMutableList()
            val newCardsRequired = DEFAULT_PLAYER_CARDS_AMOUNT - updatedPlayerCards.size
            repeat(newCardsRequired) {
                val newCard = deckAnswers.filter { it.used == 0L }.random()
                updatedPlayerCards.add(newCard)
                val index = deckAnswers.indexOfFirst { it.id == newCard.id }
                deckAnswers[index] = deckAnswers[index].copy(used = 1L)
            }
            player.copy(
                cards = updatedPlayerCards,
                state = Player.PlayerState.ANSWERING.name
            )
        }.associateBy { it.id }

        roomsDbReference.child(gameRoom.id).updateChildren(
            mapOf(
                DB_REF_PLAYERS to updatedPlayers,
                DB_REF_ANSWERS to deckAnswers
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
            answers = emptyList(),
            state = "ACTIVE"
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
            .removeValue()
        Napier.d(
            tag = TAG,
            message = "Room ${gameRoom.id} finished"
        )
    }

    companion object {
        private val TAG = RoomsRepository::class.simpleName
    }
}