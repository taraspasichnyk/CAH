package com.eleks.cah.lobby

import com.eleks.cah.base.BaseViewModel
import com.eleks.cah.domain.model.GameRound
import com.eleks.cah.domain.model.Player
import com.eleks.cah.domain.usecase.create_room.CreateRoomUseCase
import com.eleks.cah.domain.usecase.delete_not_ready_users.DeleteNotReadyUsersUseCase
import com.eleks.cah.domain.usecase.join_room.JoinRoomUseCase
import com.eleks.cah.domain.usecase.login.AnonymousLoginUseCase
import com.eleks.cah.domain.usecase.next_round.StartNextRoundUseCase
import com.eleks.cah.domain.usecase.player_state.UpdatePlayerStateUseCase
import com.eleks.cah.domain.usecase.room.GetRoomUseCase
import com.eleks.cah.domain.usecase.room_exist.RoomExistUseCase
import com.eleks.cah.lobby.LobbyContract.ActionButtonText.*
import com.eleks.cah.lobby.LobbyContract.Effect.*
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class LobbyViewModel : BaseViewModel<LobbyContract.State, LobbyContract.Effect>(
    LobbyContract.State()
) {
    private val auth: AnonymousLoginUseCase by inject()
    private val createRoom: CreateRoomUseCase by inject()
    private val roomExist: RoomExistUseCase by inject()
    private val joinRoom: JoinRoomUseCase by inject()
    private val subscribeToRoomChanges: GetRoomUseCase by inject()
    private val updatePlayerState: UpdatePlayerStateUseCase by inject()
    private val deleteNotReadyUsers: DeleteNotReadyUsersUseCase by inject()

    private lateinit var currentScreen: LobbyInnerScreen

    private val startNextRoundUseCase: StartNextRoundUseCase by inject()
    private var player: Player? = null

    init {
        scope.launch {
            val success = kotlin.runCatching {
                auth()
            }.isSuccess
            if (!success) {
                setEffect {
                    ShowError("Відсутнє інтернет з'єднання")
                }
            }
        }
    }

    var gameOwner: Boolean = true
        set(value) {
            currentScreen = if (value) {
                LobbyInnerScreen.EnterName
            } else {
                LobbyInnerScreen.EnterCode
            }

            field = value
        }

    enum class LobbyInnerScreen {
        EnterName, EnterCode, UserList
    }

    fun onBackPressed() {
        if (state.value.isLoading) return
        when (currentScreen) {
            LobbyInnerScreen.EnterName -> {
                if (gameOwner) {
                    setEffect { Navigation.MenuScreen }
                } else {
                    validateCode(state.value.code)
                    navigateToScreen(LobbyInnerScreen.EnterCode)
                }
            }

            LobbyInnerScreen.EnterCode -> {
                setEffect { Navigation.MenuScreen }
            }

            LobbyInnerScreen.UserList -> {
                setState {
                    copy(actionButtonText = Next)
                }
                validateName(state.value.name)
                navigateToScreen(LobbyInnerScreen.EnterName)
            }
        }
    }

    fun validateName(name: String) {
        //TODO fix validation
        setState {
            copy(name = name, isNextButtonEnabled = name.length > 2)
        }
    }

    fun validateCode(code: String) {
        //TODO fix validation
        setState {
            copy(code = code, isNextButtonEnabled = code.length in (5..8))
        }
    }

    fun onNextClicked() {
        when (currentScreen) {
            LobbyInnerScreen.EnterName -> {
                enterName()
            }

            LobbyInnerScreen.EnterCode -> {
                enterCode()
            }

            LobbyInnerScreen.UserList -> {
                startGame()
            }
        }
    }

    private fun enterName() {
        scope.launch {
            setState { copy(isLoading = true) }
            if (gameOwner) {
                val createdRoom = createRoom(state.value.name)
                player = createdRoom.players.first()

                setState {
                    copy(
                        code = createdRoom.inviteCode,
                        users = createdRoom.players,
                        actionButtonText = StartGame,
                        isLoading = false
                    )
                }

            } else {
                player = joinRoom(state.value.code, state.value.name)
                setState {
                    copy(actionButtonText = Ready, isLoading = false)
                }
            }

            subscribeOnUsers()
            navigateToScreen(LobbyInnerScreen.UserList)
        }
    }

    private fun enterCode() {
        scope.launch {
            setState { copy(isLoading = true) }
            val roomExist = roomExist(state.value.code)
            setState { copy(isLoading = false) }
            if (roomExist) {
                validateName(state.value.name)
                navigateToScreen(LobbyInnerScreen.EnterName)
            } else {
                setEffect { ShowError("Невірний код приєднання") }
            }
        }
    }

    private fun startGame() {
        scope.launch {
            setState { copy(isLoading = true) }
            updatePlayerState(
                roomID = state.value.code,
                playerID = player?.id.orEmpty(),
                newState = Player.PlayerState.READY
            )
            if (gameOwner) {
                deleteNotReadyUsers(state.value.code)
                startNextRoundUseCase(state.value.code)
            } else {
                setState {
                    copy(isNextButtonEnabled = false)
                }
            }
            setState { copy(isLoading = false) }
        }
    }

    private fun subscribeOnUsers() {
        scope.launch {
            subscribeToRoomChanges(state.value.code).filterNotNull().collect {
                setState {
                    if (gameOwner) {
                        val hasAtLeastOnePlayerWithReadyState =
                            it.players.find { player -> player.state == Player.PlayerState.READY } != null

                        copy(
                            users = it.players,
                            isNextButtonEnabled = hasAtLeastOnePlayerWithReadyState
                        )
                    } else {
                        copy(users = it.players)
                    }
                }
                if (it.currentRound?.state == GameRound.GameRoundState.ACTIVE) {
                    setEffect { Navigation.GameScreen(state.value.code, player?.id.orEmpty()) }
                }
            }
        }
    }

    fun onCodeCopyClicked() {
        setEffect { CopyCode(state.value.code) }
    }

    fun onCodeShareClicked() {
        setEffect { ShareCode(state.value.code) }
    }

    private fun navigateToScreen(screen: LobbyInnerScreen) {
        currentScreen = screen
        setEffect {
            when (screen) {
                LobbyInnerScreen.EnterName -> Navigation.EnterNameScreen
                LobbyInnerScreen.EnterCode -> Navigation.EnterCodeScreen
                LobbyInnerScreen.UserList -> Navigation.UsersListScreen
            }
        }
    }
}