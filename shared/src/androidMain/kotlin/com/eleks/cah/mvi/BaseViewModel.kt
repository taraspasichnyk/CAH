package com.eleks.cah.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

actual abstract class BaseViewModel<T : UiState, E : UiEvent> : ViewModel() {

    protected actual abstract val reducer: Reducer<T, E>

    val state: Flow<T>
        get() = this.reducer.state
}