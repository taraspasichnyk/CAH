//
//  PlayerEntity.swift
//  iosApp
//
//  Created by Taras Pasichnyk on 27.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation

struct PlayerEntity {

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
            isOwner: false,
            cards: AnswerCardEntity.mock,
            state: .ANSWERING
        ),
        .init(
            id: "2",
            nickname: "Taras P",
            isOwner: true,
            cards: AnswerCardEntity.mock,
            state: .ANSWERING
        )
    ]
}
