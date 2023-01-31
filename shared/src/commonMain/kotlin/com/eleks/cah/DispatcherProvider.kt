package com.eleks.cah

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

expect object DispatcherProvider {
    fun mainDispatcher() : CoroutineDispatcher
    fun backgroundDispatcher() : CoroutineDispatcher
}