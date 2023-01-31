package com.eleks.cah.mvi

import kotlinx.coroutines.flow.Flow

actual abstract class BaseViewModel<T : UiState, E : UiEvent> {

    protected actual abstract val reducer: Reducer<T, E>

    val state: Flow<T> = reducer.state

}