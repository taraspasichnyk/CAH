package com.eleks.cah.data.extensions

val String.words: List<String>
    get() = this.split("\\s+".toRegex())

val String.wordsCount: Int
    get() = this.words.size