package com.eleks.cah.menu

import com.eleks.cah.base.BaseViewModel
import com.eleks.cah.domain.model.Player
import com.eleks.cah.domain.usecase.answer.AnswerUseCase
import com.eleks.cah.domain.usecase.create_room.CreateRoomUseCase
import com.eleks.cah.domain.usecase.join_room.JoinRoomUseCase
import com.eleks.cah.domain.usecase.login.AnonymousLoginUseCase
import com.eleks.cah.domain.usecase.next_round.StartNextRoundUseCase
import com.eleks.cah.domain.usecase.player_state.UpdatePlayerStateUseCase
import com.eleks.cah.domain.usecase.room.GetRoomUseCase
import com.eleks.cah.domain.usecase.vote.VoteUseCase
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlin.random.Random

class MenuViewModel(
    private val anonymousLoginUseCase: AnonymousLoginUseCase,
    private val createRoomUseCase: CreateRoomUseCase,
    private val getRoomUseCase: GetRoomUseCase,
    private val joinRoomUseCase: JoinRoomUseCase,
    private val updatePlayerStateUseCase: UpdatePlayerStateUseCase,
    private val startNextRoundUseCase: StartNextRoundUseCase,
    private val answerUseCase: AnswerUseCase,
    private val voteUseCase: VoteUseCase,
): BaseViewModel<MenuContract.State, MenuContract.Event, MenuContract.Effect>(
    MenuContract.State()
) {
    override fun handleEvents(event: MenuContract.Event) {
        when (event) {
            is MenuContract.Event.StartNewGame -> setEffect { MenuContract.Effect.Navigation.NewGameScreen }
            is MenuContract.Event.JoinToExistingGame -> setEffect { MenuContract.Effect.Navigation.JoinGameScreen }
            is MenuContract.Event.StartSettings -> setEffect { MenuContract.Effect.Navigation.SettingsScreen }
            is MenuContract.Event.Exit -> setEffect { MenuContract.Effect.Navigation.Exit }
            else -> return
        }
    }

    fun onNewGameSelected() {
//        setEvent(MenuContract.Event.StartNewGame) TODO: Revert
        tryGameFlow()
    }

    private fun tryGameFlow() {
        scope.launch {
            anonymousLoginUseCase.invoke()

            val hostNickname = "DmytroHost"
            val gameRoom = createRoomUseCase.invoke(hostNickname)

            joinRoomUseCase.invoke(gameRoom.inviteCode, "Dmytro1")
            joinRoomUseCase.invoke(gameRoom.inviteCode, "Dmytro2")
            joinRoomUseCase.invoke(gameRoom.inviteCode, "Dmytro3")
            joinRoomUseCase.invoke(gameRoom.inviteCode, "Dmytro4")
            joinRoomUseCase.invoke(gameRoom.inviteCode, "Dmytro5")

            var roomPlayers = getRoomUseCase.invoke(gameRoom.id, scope).firstOrNull()?.players
                ?: return@launch

            Napier.d(tag = "TestTag", message = "roomPlayers = $roomPlayers")

            roomPlayers.forEach { player ->
                updatePlayerStateUseCase.invoke(gameRoom.id, player.id, Player.PlayerState.READY)
            }

            Napier.d(tag = "TestTag", message = "Start round 1")

            startNextRoundUseCase.invoke(gameRoom.id)
            roomPlayers = getRoomUseCase.invoke(gameRoom.id, scope).firstOrNull()?.players
                ?: return@launch
            Napier.d(tag = "TestTag", message = "roomPlayers = $roomPlayers")

            roomPlayers.forEach { player ->
                answerUseCase.invoke(
                    gameRoom.id,
                    playerID = player.id,
                    playerAnswers = listOf(player.cards.random().id),
                )
            }
            roomPlayers.forEach { player ->
                roomPlayers.forEach {
                    if (it.id != player.id) {
                        voteUseCase.invoke(
                            gameRoom.id,
                            it.id,
                            score = Random.nextInt(1, 4)
                        )
                    }
                }
            }

            Napier.d(tag = "TestTag", message = "Start round 2")

            startNextRoundUseCase.invoke(gameRoom.id)
            roomPlayers = getRoomUseCase.invoke(gameRoom.id, scope).firstOrNull()?.players
                ?: return@launch
            Napier.d(tag = "TestTag", message = "roomPlayers = $roomPlayers")

            roomPlayers.forEach { player ->
                answerUseCase.invoke(
                    gameRoom.id,
                    playerID = player.id,
                    playerAnswers = listOf(player.cards.random().id),
                )
            }
            roomPlayers.forEach { player ->
                roomPlayers.forEach {
                    if (it.id != player.id) {
                        voteUseCase.invoke(
                            gameRoom.id,
                            it.id,
                            score = Random.nextInt(1, 4)
                        )
                    }
                }
            }

            Napier.d(tag = "TestTag", message = "Start round 3")

            startNextRoundUseCase.invoke(gameRoom.id)
            roomPlayers = getRoomUseCase.invoke(gameRoom.id, scope).firstOrNull()?.players
                ?: return@launch
            Napier.d(tag = "TestTag", message = "roomPlayers = $roomPlayers")

            roomPlayers.forEach { player ->
                answerUseCase.invoke(
                    gameRoom.id,
                    playerID = player.id,
                    playerAnswers = listOf(player.cards.random().id),
                )
            }
            roomPlayers.forEach { player ->
                roomPlayers.forEach {
                    if (it.id != player.id) {
                        voteUseCase.invoke(
                            gameRoom.id,
                            it.id,
                            score = Random.nextInt(0, 4)
                        )
                    }
                }
            }

            Napier.d(tag = "TestTag", message = "Finish game")
            startNextRoundUseCase.invoke(gameRoom.id)
            roomPlayers = getRoomUseCase.invoke(gameRoom.id, scope).firstOrNull()?.players
                ?: return@launch
            Napier.d(tag = "TestTag", message = "roomPlayers = $roomPlayers")

            // Show game results
            val gameRoomFinished = getRoomUseCase.invoke(gameRoom.id, scope).firstOrNull()
                ?: return@launch
            Napier.d(tag = "TestTag", message = "\n")
            Napier.d(tag = "TestTag", message = "Game finished. Leaderboard:")
            gameRoomFinished.players.sortedByDescending { it.score }.forEach {
                Napier.d(
                    tag = "TestTag",
                    message = "${it.nickname} - ${it.score} points"
                )
            }
        }
    }

    fun onJoinGameSelected() {
        setEvent(MenuContract.Event.JoinToExistingGame)
    }

    fun onSettingsSelected() {
        setEvent(MenuContract.Event.StartSettings)
    }

    fun onExitSelected() {
        setEvent(MenuContract.Event.Exit)
    }

    companion object {
        const val NAVIGATION_EFFECTS_KEY = "menu_navigation_effects"
    }
}