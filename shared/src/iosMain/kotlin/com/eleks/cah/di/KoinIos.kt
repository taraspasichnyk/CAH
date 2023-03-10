package com.eleks.cah.di

import com.eleks.cah.base.BaseViewModel
import com.eleks.cah.domain.model.PlayerID
import com.eleks.cah.domain.model.RoomID
import com.eleks.cah.domain.usecase.login.AnonymousLoginUseCase
import com.eleks.cah.game.GameViewModel
import com.eleks.cah.menu.MenuViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import com.eleks.cah.lobby.LobbyViewModel

actual val viewModelModule: Module
    get() = module {
        single { MenuViewModel() }
        factory {
            val vm = LobbyViewModel()
            vm.gameOwner = it.get()
            vm
        }
        factory { params ->
            GameViewModel(params[0], params[1])
        }
    }

fun initKoin() {
    startKoin {
        modules(allModules())
    }
}

//service locator
object Injector : KoinComponent {
    val menuViewModel: MenuViewModel by inject()
    val anonymousLogin: AnonymousLoginUseCase by inject()

    val lobbyOwnerViewModel: LobbyViewModel
        get() {
            val vm: LobbyViewModel by inject { parametersOf(true) }
            return vm
        }


    val lobbyViewModel: LobbyViewModel
        get() {
            val vm: LobbyViewModel by inject { parametersOf(false) }
            return vm
        }

    fun makeGameViewModel(roomID: RoomID, playerID: PlayerID): GameViewModel {
        val vm: GameViewModel by inject { parametersOf(roomID, playerID) }
        return vm
    }
}