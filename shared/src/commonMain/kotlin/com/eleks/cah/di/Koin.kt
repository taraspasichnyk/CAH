package com.eleks.cah.di

import com.eleks.cah.data.repository.ImagesRepository
import com.eleks.cah.data.firebase.FirebaseAuth
import com.eleks.cah.data.repository.PlayersRepositoryImpl
import com.eleks.cah.data.repository.RoomsRepositoryImpl
import com.eleks.cah.domain.repository.PlayersRepository
import com.eleks.cah.domain.repository.RoomsRepository
import com.eleks.cah.domain.usecase.answer.AnswerUseCase
import com.eleks.cah.domain.usecase.answer.AnswerUseCaseImpl
import com.eleks.cah.domain.usecase.create_room.CreateRoomUseCase
import com.eleks.cah.domain.usecase.create_room.CreateRoomUseCaseImpl
import com.eleks.cah.domain.usecase.delete_not_ready_users.DeleteNotReadyUsersUseCase
import com.eleks.cah.domain.usecase.delete_not_ready_users.DeleteNotReadyUsersUseCaseImpl
import com.eleks.cah.domain.usecase.join_room.JoinRoomUseCase
import com.eleks.cah.domain.usecase.join_room.JoinRoomUseCaseImpl
import com.eleks.cah.domain.usecase.login.AnonymousLoginUseCase
import com.eleks.cah.domain.usecase.login.AnonymousLoginUseCaseImpl
import com.eleks.cah.domain.usecase.next_round.StartNextRoundUseCase
import com.eleks.cah.domain.usecase.next_round.StartNextRoundUseCaseImpl
import com.eleks.cah.domain.usecase.player_state.UpdatePlayerStateUseCase
import com.eleks.cah.domain.usecase.player_state.UpdatePlayerStateUseCaseImpl
import com.eleks.cah.domain.usecase.room.GetRoomUseCase
import com.eleks.cah.domain.usecase.room.GetRoomUseCaseImpl
import com.eleks.cah.domain.usecase.room_exist.RoomExistUseCase
import com.eleks.cah.domain.usecase.room_exist.RoomExistUseCaseImpl
import com.eleks.cah.domain.usecase.vote.VoteUseCase
import com.eleks.cah.domain.usecase.vote.VoteUseCaseImpl
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.database.database
import dev.gitlive.firebase.initialize
import org.koin.core.module.Module
import org.koin.dsl.module

expect val viewModelModule: Module

// Data
private val dataModule by lazy {
    module {
        single { FirebaseAuth() }

        single {
            Firebase.database.reference()
        }

        single<RoomsRepository> {
            RoomsRepositoryImpl(
                databaseReference = get()
            )
        }

        single<PlayersRepository> {
            PlayersRepositoryImpl(
                databaseReference = get()
            )
        }
    }
}

// Domain
private val domainModule by lazy {
    module {
        single<AnonymousLoginUseCase> {
            AnonymousLoginUseCaseImpl(
                firebaseAuth = get()
            )
        }

        single<CreateRoomUseCase> {
            CreateRoomUseCaseImpl(
                roomsRepository = get()
            )
        }

        single<GetRoomUseCase> {
            GetRoomUseCaseImpl(
                roomsRepository = get()
            )
        }
        single<RoomExistUseCase> {
            RoomExistUseCaseImpl(
                roomsRepository = get()
            )
        }

        single<JoinRoomUseCase> {
            JoinRoomUseCaseImpl(
                roomsRepository = get()
            )
        }

        single<UpdatePlayerStateUseCase> {
            UpdatePlayerStateUseCaseImpl(
                playersRepository = get()
            )
        }

        single<StartNextRoundUseCase> {
            StartNextRoundUseCaseImpl(
                roomsRepository = get()
            )
        }

        single<AnswerUseCase> {
            AnswerUseCaseImpl(
                playersRepository = get()
            )
        }

        single<VoteUseCase> {
            VoteUseCaseImpl(
                playersRepository = get()
            )
        }
        single<DeleteNotReadyUsersUseCase> {
            DeleteNotReadyUsersUseCaseImpl(
                playersRepository = get()
            )
        }
    }
}

// Repositories
val repositoryModule = module {
    single { ImagesRepository() }
}

// Common App Definitions
fun allModules(context: Any? = null): List<Module> {
    Firebase.initialize(context)
    return listOf(
        viewModelModule,
        domainModule,
        dataModule,
        repositoryModule
    )
}