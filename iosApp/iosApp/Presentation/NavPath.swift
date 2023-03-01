//
//  NavPath.swift
//  iosApp
//
//  Created by Artem Yelizarov on 09.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

/// Local path representing navigation that starts from `Main Menu`
enum NavPath: Equatable, Hashable {
    case enterCode(LobbyViewModel)
    case enterName(LobbyViewModel)
    case lobby(LobbyViewModel)
    case yourCards(GameViewModel)
    case leaderboard(GameViewModel)
}

// MARK: - ViewModels

extension NavPath {
    var lobbyViewModel: LobbyViewModel? {
        switch self {
        case .enterCode(let vm),
                .enterName(let vm),
                .lobby(let vm):
            return vm
        default:
            return nil
        }
    }

    var gameViewModel: GameViewModel? {
        switch self {
        case .yourCards(let vm), .leaderboard(let vm):
            return vm
        default:
            return nil
        }
    }
}

// MARK: - Operators

extension NavPath {
    static func ~=(_ lhs: NavPath, rhs: NavPath) -> Bool {
        switch (lhs, rhs) {
        case (.enterCode, .enterCode),
            (.enterName, .enterName),
            (.lobby, .lobby),
            (.yourCards, .yourCards):
            return true
        default:
            return false
        }
    }
}

// MARK: - Navigation

extension [NavPath] {
    mutating func navigate(to path: NavPath) {
        if let pathIndex = firstIndex(where: {  $0 ~= path }) {
            removeAll(after: pathIndex)
        } else {
            append(path)
        }
    }
}
