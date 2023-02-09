package com.eleks.cah.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope

actual abstract class ScopedViewModel : ViewModel() {
    actual val scope: CoroutineScope
        get() = viewModelScope
}