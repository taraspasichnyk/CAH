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
    let player: PlayerEntity
    let playerAnswers: [AnswerCardEntity]
    let score: Int

    static let mock: [RoundPlayerAnswerEntity] = {
        return PlayerEntity.mock.map {
            .init(
                player: $0,
                playerAnswers: [
                    AnswerCardEntity(id: UUID().uuidString, text: "Answer 0."),
                    AnswerCardEntity(id: UUID().uuidString, text: "Very really unjustifiably long answer for this question that was much longer in my head then I initially suspected."),
                ],
                score: 42
            )
        }
    }()
}
