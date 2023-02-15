package com.eleks.cah.lobby

import com.eleks.cah.base.UiEffect
import com.eleks.cah.base.UiState
import com.eleks.cah.domain.model.Player
import com.eleks.cah.lobby.LobbyContract.ActionButtonText.Next

interface LobbyContract {
    enum class ActionButtonText{
        Next, Ready, StartGame
    }
    data class State(
        val code: String = "",
        val name: String = "",
        val users:List<Player> = emptyList(),
        val isLoading: Boolean = false,
        val isNextButtonEnabled: Boolean = false,
        val actionButtonText: ActionButtonText = Next
    ) : UiState

    sealed class Effect : UiEffect {
        data class ShareCode(val code: String) : Effect()
        data class CopyCode(val code: String) : Effect()
        data class ShowError(val message: String) : Effect()

        sealed class Navigation : Effect() {
            object MenuScreen : Navigation()
            object EnterNameScreen : Navigation()
            object EnterCodeScreen : Navigation()
            object UsersListScreen : Navigation()
            data class GameScreen(val roomId: String) : Navigation()
        }
    }
}