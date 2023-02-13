package com.eleks.cah.android.router

sealed class MainRoute(val path: String, vararg arguments: String = emptyArray()) {
    operator fun invoke() = pathWithArgumentsMask

    val arguments: List<String>
    init {
        this.arguments = arguments.toList()
    }
    private val pathWithArgumentsMask: String
        get() {
            if (arguments.isEmpty()) {
                return path
            }
            return path + arguments.fold("") { total, item ->
                "$total/{$item}"
            }
        }

    protected fun getPathWithArguments(vararg arguments: Any): String {
        if (arguments.isEmpty()) {
            return path
        }
        return path + arguments.fold("") { total, item ->
            "$total/$item"
        }
    }

    object Menu : MainRoute("menu")
    object Lobby : MainRoute("lobby", "create-new-game") {
        fun getPath(createNewGame: Boolean): String {
            return getPathWithArguments(createNewGame)
        }
    }

    object Round : MainRoute("round", "number") {
        fun getPath(number: Int): String {
            return getPathWithArguments(number)
        }
    }

    object PostRoundScreen: MainRoute("postround", "number"){
        fun getPath(number: Int): String {
            return getPathWithArguments(number)
        }
    }

    object PreRoundScreen : MainRoute("preround", "number") {
        fun getPath(number: Int): String {
            return getPathWithArguments(number)
        }
    }
}