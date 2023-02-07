package com.eleks.cah.base

import kotlinx.coroutines.CoroutineScope

expect abstract class ScopedViewModel() {

    val scope: CoroutineScope

}