package com.eleks.cah

import platform.UIKit.UIDevice

class IosPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName
}

actual fun getPlatform(): Platform {
    return IosPlatform()
}