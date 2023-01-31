package com.eleks.cah.mvi

import kotlinx.coroutines.flow.Flow

expect abstract class BaseViewModel<T : UiState, E : UiEvent>() {

    protected abstract val reducer: Reducer<T, E>

}