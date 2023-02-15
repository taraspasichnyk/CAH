//
//  EnterScreenStage.swift
//  iosApp
//
//  Created by Artem Yelizarov on 09.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation

/// Local `Enter Screen` stage that reflects its variant
/// Might potentially be replaced with viewmodels from shared module, when they're ready
enum EnterScreenStage {
    case playerName
    case roomCode
}

// MARK: - Convenience

extension EnterScreenStage {
    var prompt: String {
        switch self {
        case .playerName: return "Введіть імʼя"
        case .roomCode: return "Введіть код гри"
        }
    }

    var placeholder: String {
        switch self {
        case .playerName: return "Ваше імʼя"
        case .roomCode: return "XXXXXXXX"
        }
    }
}
