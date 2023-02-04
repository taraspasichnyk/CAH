package com.eleks.cah.di

import com.eleks.cah.game.GameViewModel
import com.eleks.cah.menu.MenuViewModel
import org.koin.core.Koin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.KoinContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

inline fun <reified T> get() =
    object : KoinComponent {
        val value: T by inject()
    }.value

actual val viewModelModule: Module
    get() = module {
        single { GameViewModel() }
        single { MenuViewModel() }
    }

fun initKoin() {
    startKoin {
        modules(viewModelModule)
    }
}