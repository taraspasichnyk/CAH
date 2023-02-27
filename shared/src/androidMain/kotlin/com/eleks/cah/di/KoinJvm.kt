package com.eleks.cah.di

import com.eleks.cah.game.GameViewModel
import com.eleks.cah.lobby.LobbyViewModel
import com.eleks.cah.menu.MenuViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

actual val viewModelModule: Module
    get() = module {
        viewModelOf(::MenuViewModel)
        viewModel { params -> GameViewModel(params[0], params[1]) }
        viewModelOf(::LobbyViewModel)
    }