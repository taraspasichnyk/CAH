package com.eleks.cah.mvi

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface UiState
interface UiEvent
abstract class Reducer<S : UiState, E : UiEvent>(initialVal: S) {

    private val _state: MutableStateFlow<S> = MutableStateFlow(initialVal)
    val state: StateFlow<S>
        get() = _state

    fun sendEvent(event: E) {
        reduce(_state.value, event)
    }

    protected fun setState(newState: S) {
        _state.tryEmit(newState)
    }

    protected abstract fun reduce(oldState: S, event: E)
}

