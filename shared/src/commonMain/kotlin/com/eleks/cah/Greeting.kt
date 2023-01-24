package com.eleks.cah

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }

    fun getPlayer(): Player {
        return Player("Test")
    }
}