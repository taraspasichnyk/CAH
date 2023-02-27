//
//  LobbyContractState+Extensions.swift
//  iosApp
//
//  Created by Artem Yelizarov on 17.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

extension LobbyContractState {
    var buttonText: String {
        switch self.actionButtonText {
        case .ready: return "Готовий"
        case .startgame: return "Розпочати гру"
        default: return "Далі"
        }
    }
}
