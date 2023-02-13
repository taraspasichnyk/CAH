package com.eleks.cah.domain.repository

import com.eleks.cah.data.model.GameRoomDTO
import com.eleks.cah.domain.model.RoomID
import kotlinx.coroutines.flow.Flow

interface RoomsRepository {
    suspend fun createNewRoom(hostNickname: String): GameRoomDTO

    suspend fun getRoomByIdFlow(roomID: RoomID): Flow<GameRoomDTO>

    suspend fun joinRoom(
        inviteCode: String,
        nickname: String
    )

    suspend fun startNextRound(roomID: RoomID)
}