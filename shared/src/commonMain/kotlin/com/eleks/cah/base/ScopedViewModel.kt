package com.eleks.cah.base

import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinComponent

expect abstract class ScopedViewModel() :KoinComponent{

    val scope: CoroutineScope

}