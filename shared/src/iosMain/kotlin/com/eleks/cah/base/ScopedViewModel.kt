package com.eleks.cah.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

actual abstract class ScopedViewModel {

    actual val scope: CoroutineScope
        get() = CoroutineScope(SupervisorJob() + Dispatchers.Default)
}