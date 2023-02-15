package com.eleks.cah.di

import com.eleks.cah.domain.usecase.login.AnonymousLoginUseCase
import com.eleks.cah.game.GameViewModel
import com.eleks.cah.menu.MenuViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val viewModelModule: Module
    get() = module {
        single { GameViewModel() }
        single { MenuViewModel() }
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
}