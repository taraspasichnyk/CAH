package com.eleks.cah.base

import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent

actual abstract class ScopedViewModel : KoinComponent {

    actual val scope: CoroutineScope
        get() = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    fun onCleared() {
        scope.cancel()
    }
}