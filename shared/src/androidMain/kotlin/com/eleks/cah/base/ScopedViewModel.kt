package com.eleks.cah.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinComponent

actual abstract class ScopedViewModel : ViewModel(), KoinComponent {
    actual val scope: CoroutineScope
        get() = viewModelScope
}