package com.eleks.cah.data.extensions

import com.eleks.cah.domain.exceptions.RoomNotFoundException
import com.eleks.cah.data.model.GameRoomDTO
import com.eleks.cah.domain.model.RoomID
import dev.gitlive.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.firstOrNull

suspend fun DatabaseReference.roomOrException(
    roomID: RoomID,
    action: suspend (GameRoomDTO) -> Unit
) = this.child(roomID)
    .valueEvents
    .firstOrNull()?.takeIf { it.exists }?.let {
        val gameRoomDto = it.value<GameRoomDTO>()
        action(gameRoomDto)
    } ?: throw RoomNotFoundException(roomID)