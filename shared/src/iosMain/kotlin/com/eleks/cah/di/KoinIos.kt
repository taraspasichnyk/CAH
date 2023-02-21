package com.eleks.cah.di

import com.eleks.cah.base.BaseViewModel
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
        single { GameViewModel() }
        single { MenuViewModel() }
        factory {
            val vm = LobbyViewModel()
            vm.gameOwner = it.get()
            vm
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
    val gameViewModel: GameViewModel by inject()
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
}