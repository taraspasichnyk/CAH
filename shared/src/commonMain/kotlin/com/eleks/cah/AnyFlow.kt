package com.eleks.cah

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow


class AnyFlow<T>(source: Flow<T>) : Flow<T> by source {

    fun collect(onEach: (T) -> Unit, onCompletion: (cause: Throwable?) -> Unit): Cancellable {
        val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

        scope.launch {
            try {
                collect {
                    onEach(it)
                }

                onCompletion(null)
            } catch (e: Throwable) {
                onCompletion(e)
            }
        }

        return object : Cancellable {
            override fun cancel() {
                scope.cancel()
            }
        }
    }
}