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
    let playerAnswers: [String]
    let score: Int

    static let mock: [RoundPlayerAnswerEntity] = {
        return PlayerEntity.mock.map { .init(player: $0, playerAnswers: [], score: 42) }
    }()
}