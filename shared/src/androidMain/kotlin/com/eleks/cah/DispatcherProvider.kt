package com.eleks.cah

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual object DispatcherProvider {
    actual fun mainDispatcher() : CoroutineDispatcher = Dispatchers.Main

    actual fun backgroundDispatcher() : CoroutineDispatcher = Dispatchers.Default
}
