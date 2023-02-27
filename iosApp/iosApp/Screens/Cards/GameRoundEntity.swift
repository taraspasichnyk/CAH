//
//  GameRound.swift
//  iosApp
//
//  Created by Taras Pasichnyk on 27.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation

struct GameRoundEntity {

    enum State: String {
        case ACTIVE, VOTING, FINISHED
    }

    let id: String
    let number: Int
    let questionCard: QuestionCardEntity
    let playerCards: [RoundPlayerAnswerEntity]
    let state: State
}
