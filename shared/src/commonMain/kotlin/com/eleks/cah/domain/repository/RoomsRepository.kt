package com.eleks.cah.domain.repository

import com.eleks.cah.data.model.GameRoomDTO
import com.eleks.cah.data.model.PlayerDTO
import com.eleks.cah.domain.model.RoomID
import kotlinx.coroutines.flow.Flow

interface RoomsRepository {
    suspend fun createNewRoom(hostNickname: String): GameRoomDTO

    suspend fun getRoomByIdFlow(roomID: RoomID): Flow<GameRoomDTO>

    suspend fun getRoomIfExist(roomID: RoomID): GameRoomDTO?

    suspend fun joinRoom(
        inviteCode: String,
        nickname: String
    ): PlayerDTO

    suspend fun startNextRound(roomID: RoomID)
}