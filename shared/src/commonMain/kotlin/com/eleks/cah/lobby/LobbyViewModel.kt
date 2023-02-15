package com.eleks.cah.lobby

import com.eleks.cah.base.BaseViewModel
import com.eleks.cah.lobby.LobbyContract.Effect.CopyCode
import com.eleks.cah.lobby.LobbyContract.Effect.Navigation
import com.eleks.cah.lobby.LobbyContract.Effect.ShareCode
import com.eleks.cah.models.UserInLobby
import kotlin.random.Random

class LobbyViewModel(val gameOwner: Boolean) :
    BaseViewModel<LobbyContract.State, LobbyContract.Effect>(
        LobbyContract.State()
    ) {
    private var currentScreen: LobbyInnerScreen

    init {
        currentScreen = if (gameOwner) {
            LobbyInnerScreen.EnterName
        } else {
            LobbyInnerScreen.EnterCode
        }
    }

    enum class LobbyInnerScreen {
        EnterName, EnterCode, UserList
    }

    fun onBackPressed() {
        when (currentScreen) {
            LobbyInnerScreen.EnterName -> {
                if (gameOwner) {
                    setEffect {
                        Navigation.MenuScreen
                    }
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
                if(gameOwner){
                    //TODO generate code via UseCase
                    setState {
                        copy(code="12312413")
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
        when (screen) {
            LobbyInnerScreen.EnterName -> {
                setEffect { Navigation.EnterNameScreen }
            }
            LobbyInnerScreen.EnterCode -> {
                setEffect { Navigation.EnterCodeScreen }
            }
            LobbyInnerScreen.UserList -> {
                setEffect { Navigation.UsersListScreen }
            }
        }
    }
}