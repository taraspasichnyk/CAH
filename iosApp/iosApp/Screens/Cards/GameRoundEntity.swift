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
    let answers: [RoundPlayerAnswerEntity]
    let state: State

    static let mock: GameRoundEntity = GameRoundEntity(
        id: "123",
        number: 1,
        questionCard: QuestionCardEntity(id: "1", text: "Вітаємо! Ви були обрані для нашої літньої інтернатури. Хоча ми не можемо пропонувати зарплату, ми можемо пропонувати \"вам\" ____ .", question: "Вітаємо! Ви були обрані для нашої літньої інтернатури. Хоча ми не можемо пропонувати зарплату, ми можемо пропонувати \"вам\" ____ .", gaps: [0, 1, 2]),
        answers: RoundPlayerAnswerEntity.mock,
        state: .VOTING
    )
}
