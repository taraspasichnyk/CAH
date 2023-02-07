package com.eleks.cah.base

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

interface UiState
interface UiEvent
interface UiEffect

abstract class BaseViewModel<State : UiState, Event : UiEvent, Effect : UiEffect>(
    initialState: State
): ScopedViewModel() {
    private val _state = MutableStateFlow<State>(initialState)
    val state: StateFlow<State>
        get() = _state

    private val _event = MutableSharedFlow<Event>()

    private val _effect = Channel<Effect>()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeToEvents()
    }

    fun setEvent(event: Event) {
        scope.launch { _event.emit(event) }
    }

    protected fun setState(reduce: State.() -> State) {
        val newState = _state.value.reduce()
        _state.value = newState
    }

    private fun subscribeToEvents() {
        scope.launch {
            _event.collect {
                handleEvents(it)
            }
        }
    }

    abstract fun handleEvents(event: Event)

    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        scope.launch { _effect.send(effectValue) }
    }



}