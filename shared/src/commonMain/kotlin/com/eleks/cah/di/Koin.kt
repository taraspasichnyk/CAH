package com.eleks.cah.di

import com.eleks.cah.data.ImagesRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

//Repositories
val repositoryModule = module {
    singleOf(::ImagesRepository)
}

expect val viewModelModule: Module

fun allModules() = listOf(
    repositoryModule,
    viewModelModule
)