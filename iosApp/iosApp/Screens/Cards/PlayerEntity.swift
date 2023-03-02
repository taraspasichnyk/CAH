//
//  PlayerEntity.swift
//  iosApp
//
//  Created by Taras Pasichnyk on 27.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation

struct PlayerEntity: Equatable {

    enum State: String {
        case NOT_READY, READY, ANSWERING, ANSWER_SUBMITTED, VOTING, VOTE_SUBMITTED
    }

    let id: String
    let nickname: String
    let isOwner: Bool
    let cards: [AnswerCardEntity]
    let state: State

    static let mock: [PlayerEntity] = [
        .init(
            id: "1",
            nickname: "@rt3m",
            isOwner: true,
            cards: AnswerCardEntity.mock,
            state: .ANSWERING
        ),
        .init(
            id: "2",
            nickname: "Taras P",
            isOwner: false,
            cards: AnswerCardEntity.mock,
            state: .ANSWERING
        )
        ,
        .init(
            id: "3",
            nickname: "Andriy P",
            isOwner: false,
            cards: AnswerCardEntity.mock,
            state: .ANSWERING
        )
        ,
        .init(
            id: "4",
            nickname: "Taras Y",
            isOwner: false,
            cards: AnswerCardEntity.mock,
            state: .ANSWERING
        )
        ,
        .init(
            id: "5",
            nickname: "Dmytro S",
            isOwner: false,
            cards: AnswerCardEntity.mock,
            state: .ANSWERING
        )
        ,
        .init(
            id: "6",
            nickname: "Andriy K",
            isOwner: false,
            cards: AnswerCardEntity.mock,
            state: .ANSWERING
        )
        ,
        .init(
            id: "7",
            nickname: "Oleh S",
            isOwner: false,
            cards: AnswerCardEntity.mock,
            state: .ANSWERING
        )
    ]
}
