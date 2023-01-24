package com.eleks.cah

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform