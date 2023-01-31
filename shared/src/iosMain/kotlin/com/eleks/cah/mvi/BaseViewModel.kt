package com.eleks.cah.mvi

import com.eleks.cah.AnyFlow
import io.github.aakira.napier.Napier
import kotlinx.coroutines.withContext
import platform.Foundation.NSThread
import kotlin.native.concurrent.freeze
import kotlin.properties.ObservableProperty

actual abstract class BaseViewModel<T : UiState, E : UiEvent>{

    protected actual abstract val reducer: Reducer<T, E>

    val state: AnyFlow<T>
        get() = AnyFlow(reducer.state)

}