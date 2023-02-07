package com.eleks.cah.android

enum class Route(val path: String) {
    Menu("menu"),
    NewGame("new-game"),
    Lobby("lobby"),
    Round("round"),
    PreRoundScreen("preround/{number}"),
    JoinGame("join-game"),
    Settings("settings");
}