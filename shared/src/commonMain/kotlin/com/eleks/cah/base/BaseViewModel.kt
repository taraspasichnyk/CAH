package com.eleks.cah.base

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface UiState
interface UiEffect

abstract class BaseViewModel<State : UiState, Effect : UiEffect>(
    initialState: State
): ScopedViewModel() {
    private val _state = MutableStateFlow<State>(initialState)
    val state: StateFlow<State>
        get() = _state

    private val _effect = Channel<Effect>()
    val effect = _effect.receiveAsFlow()

    protected fun setState(reduce: State.() -> State) {
        val newState = _state.value.reduce()
        _state.value = newState
    }
    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        scope.launch { _effect.send(effectValue) }
    }
}