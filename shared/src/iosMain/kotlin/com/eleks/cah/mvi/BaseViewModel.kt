package com.eleks.cah.mvi

import com.eleks.cah.AnyFlow
import kotlinx.coroutines.flow.Flow

actual abstract class BaseViewModel<T : UiState, E : UiEvent> {

    protected actual abstract val reducer: Reducer<T, E>

    val state: AnyFlow<T> = AnyFlow(reducer.state)

}