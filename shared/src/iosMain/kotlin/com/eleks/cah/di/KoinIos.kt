package com.eleks.cah.di

import com.eleks.cah.domain.usecase.login.AnonymousLoginUseCase
import com.eleks.cah.game.GameViewModel
import com.eleks.cah.lobby.LobbyViewModel
import com.eleks.cah.menu.MenuViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val viewModelModule: Module
    get() = module {
        single { GameViewModel() }
        single { MenuViewModel() }
        single(named("lobbyOwner")) {
            LobbyViewModel(true)
        }
        single(named("lobby")) {
            LobbyViewModel(false)
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

    // Temporary solution until we find a better way to do it
    val lobbyOwnerViewModel: LobbyViewModel by inject(named("lobbyOwner"))
    val lobbyViewModel: LobbyViewModel by inject(named("lobby"))
}