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

    object Game : MainRoute("game", "roomId", "playerId") {
        fun getPath(roomId: String, playerId: String): String {
            return getPathWithArguments(roomId, playerId)
        }
    }
}