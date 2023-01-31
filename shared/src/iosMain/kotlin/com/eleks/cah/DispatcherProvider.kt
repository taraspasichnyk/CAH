package com.eleks.cah

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke

actual object DispatcherProvider {
    actual fun mainDispatcher() : CoroutineDispatcher = NSLooperDispatcher

    actual fun backgroundDispatcher() : CoroutineDispatcher = Dispatchers.Default
}
