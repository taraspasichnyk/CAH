package com.eleks.cah.base

import kotlinx.coroutines.*

actual abstract class ScopedViewModel {

    actual val scope: CoroutineScope
        get() = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    fun onCleared() {
        scope.cancel()
    }
}