//
//  MenuEffectProcessor.swift
//  iosApp
//
//  Created by Artem Yelizarov on 17.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

final class MenuEffectProcessor {
    @Binding private var navState: [NavPath]
    private let injector: Injector
    private let alert: AlertState

    init(
        injector: Injector = Injector.shared,
        navState: Binding<[NavPath]>,
        alert: AlertState
    ) {
        self.injector = injector
        self._navState = navState
        self.alert = alert
    }

    func process(_ effect: MenuContractEffect, subscribeToLobbyEffects: (LobbyViewModel) -> Void) {
        switch effect {
        case is MenuContractEffect.NavigationNewGameScreen:
            let lobbyVm = injector.lobbyOwnerViewModel
            subscribeToLobbyEffects(lobbyVm)
            navState.navigate(to: .enterName(lobbyVm))
        case is MenuContractEffect.NavigationJoinGameScreen:
            let lobbyVm = injector.lobbyViewModel
            subscribeToLobbyEffects(lobbyVm)
            navState.append(.enterCode(lobbyVm))
        default:
            alert.isPresentingNoFeature = true
        }
    }
}
