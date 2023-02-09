package com.eleks.cah.di

import com.eleks.cah.game.GameViewModel
import com.eleks.cah.menu.MenuViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

actual val viewModelModule: Module
    get() = module {
        viewModelOf(::MenuViewModel)
        viewModelOf(::GameViewModel)
    }