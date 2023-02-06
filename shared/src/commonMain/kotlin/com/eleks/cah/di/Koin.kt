package com.eleks.cah.di

import org.koin.core.module.Module

expect val viewModelModule: Module

//Repositories

// Common App Definitions
fun appModule() = listOf(
    viewModelModule,
)