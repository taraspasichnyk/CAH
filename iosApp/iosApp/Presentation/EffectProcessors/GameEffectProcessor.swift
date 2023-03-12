//
//  GameEffectProcessor.swift
//  iosApp
//
//  Created by Taras Pasichnyk on 23.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

final class GameEffectProcessor {
    @Binding private var navState: [NavPath]
    @Binding private var gameNavState: GameNavState
    private let alertState: AlertState

    init(
        navState: Binding<[NavPath]>,
        gameNavState: Binding<GameNavState>,
        alertState: AlertState
    ) {
        self._navState = navState
        self._gameNavState = gameNavState
        self.alertState = alertState
    }

    func process(_ effect: GameContractEffect) {
        switch effect {
        case is GameContractEffect.NavigationYourCards:
            gameNavState = .yourCards
        case is GameContractEffect.NavigationPreRound:
            gameNavState = .preround
        case is GameContractEffect.NavigationRound:
            // TODO: Support if there is a good-looking solution
            break
        case is GameContractEffect.NavigationVoting:
            gameNavState = .voting
        case is GameContractEffect.NavigationResults:
            gameNavState = .leaderBoard
        case is GameContractEffect.NavigationMenu:
            navState = []
        default:
            alertState.presentedAlertType = .noFeature
        }
    }
}
