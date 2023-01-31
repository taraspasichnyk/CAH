package com.eleks.cah

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

actual fun init(){
    Napier.base(DebugAntilog())
}