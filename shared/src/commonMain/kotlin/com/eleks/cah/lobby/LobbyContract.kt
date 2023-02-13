package com.eleks.cah.lobby

import com.eleks.cah.base.UiEffect
import com.eleks.cah.base.UiState
import com.eleks.cah.models.UserInLobby

interface LobbyContract {
    data class State(
        val isLoading: Boolean = false,
        val isNameValid: Boolean = false,
        val isCodeValid: Boolean = false,
        val code: String = "",
        val name: String = "",
        val users:List<UserInLobby> = emptyList()
    ) : UiState

    sealed class Effect : UiEffect {
        data class ShareCode(val code: String) : Effect()
        data class CopyCode(val code: String) : Effect()

        sealed class Navigation : Effect() {
            object MenuScreen : Navigation()
            object EnterNameScreen : Navigation()
            object EnterCodeScreen : Navigation()
            object UsersListScreen : Navigation()
            object YourCardsScreen : Navigation()
        }
    }
}