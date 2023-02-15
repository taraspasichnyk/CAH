package com.eleks.cah.domain.exceptions

import com.eleks.cah.domain.model.PlayerID
import com.eleks.cah.domain.model.RoomID

class FailedToJoinRoomException(
    nickname: String,
    roomId: RoomID
): IllegalStateException("Failed to add player ($nickname) to the room ($roomId)")

class RoomNotFoundException(
    roomId: RoomID
): IllegalArgumentException("No room ID=$roomId found")

class PlayerNotFoundException(
    playerID: PlayerID
): IllegalArgumentException("No player ID=$playerID found")

class RoomNoCurrentRoundException(
    roomId: RoomID
): IllegalStateException("Room ID=$roomId: currentRound not found")