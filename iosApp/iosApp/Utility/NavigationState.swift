//
//  NavigationState.swift
//  iosApp
//
//  Created by Artem Yelizarov on 09.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

/// Local state representing navigation that starts from `Main Menu`
enum NavigationState: Equatable, Hashable {
    case enterCode(LobbyViewModel)
    case enterName(LobbyViewModel)
    case lobby(LobbyViewModel)
    case yourCards
}

// MARK: - ViewModels

extension NavigationState {
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
}

// MARK: - Operators

extension NavigationState {
    static func ~=(_ lhs: NavigationState, rhs: NavigationState) -> Bool {
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

extension [NavigationState] {
    mutating func navigate(to path: NavigationState) {
        if let pathIndex = firstIndex(where: {  $0 ~= path }) {
            removeAll(after: pathIndex)
        } else {
            append(path)
        }
    }
}
