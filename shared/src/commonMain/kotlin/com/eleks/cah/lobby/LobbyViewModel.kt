package com.eleks.cah.lobby

import com.eleks.cah.base.BaseViewModel
import com.eleks.cah.lobby.LobbyContract.Effect.CopyCode
import com.eleks.cah.lobby.LobbyContract.Effect.Navigation
import com.eleks.cah.lobby.LobbyContract.Effect.ShareCode
import com.eleks.cah.models.UserInLobby
import kotlin.random.Random

class LobbyViewModel : BaseViewModel<LobbyContract.State, LobbyContract.Effect>(
    LobbyContract.State()
) {
    private lateinit var currentScreen: LobbyInnerScreen

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
        when (currentScreen) {
            LobbyInnerScreen.EnterName -> {
                if (gameOwner) {
                    setEffect { Navigation.MenuScreen }
                } else {
                    navigateToScreen(LobbyInnerScreen.EnterCode)
                }
            }
            LobbyInnerScreen.EnterCode -> {
                setEffect { Navigation.MenuScreen }
            }
            LobbyInnerScreen.UserList -> {
                navigateToScreen(LobbyInnerScreen.EnterName)
            }
        }
    }

    fun validateName(name: String) {
        //TODO add validation
        setState {
            copy(name = name)
        }
    }

    fun validateCode(code: String) {
        //TODO add validation
        setState {
            copy(code = code)
        }
    }

    fun onNextClicked() {
        when (currentScreen) {
            LobbyInnerScreen.EnterName -> {
                setState {
                    //TODO get users usecase
                    val users = (1..10).map {
                        UserInLobby("Name $it", it == 1, Random.nextBoolean())
                    }
                    copy(users = users)
                }
                if (gameOwner) {
                    //TODO generate code via UseCase
                    setState {
                        copy(code = "12312413")
                    }
                }
                navigateToScreen(LobbyInnerScreen.UserList)
            }
            LobbyInnerScreen.EnterCode -> {
                navigateToScreen(LobbyInnerScreen.EnterName)
            }
            LobbyInnerScreen.UserList -> {
                setEffect { Navigation.YourCardsScreen }
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