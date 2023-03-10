//
//  RoundPlayerAnswerEntity.swift
//  iosApp
//
//  Created by Taras Pasichnyk on 27.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation

struct RoundPlayerAnswerEntity: Identifiable {
    var id: UUID = UUID()
    let playerId: String
    let playerAnswers: [AnswerCardEntity]
    var score: Int

    static let mock: [RoundPlayerAnswerEntity] = {
        return PlayerEntity.mock.map {
            .init(
                playerId: $0.id,
                playerAnswers: [
                    AnswerCardEntity(id: UUID().uuidString, text: "ТОБІ ПОТРІБНО ПОБУДУВАТИ ДОДАТКОВІ ПІЛОНИ."),
                    AnswerCardEntity(id: UUID().uuidString, text: "Very really unjustifiably long answer for this question that was much longer in my head then I initially suspected."),
                ],
                score: 42
            )
        }
    }()
}
