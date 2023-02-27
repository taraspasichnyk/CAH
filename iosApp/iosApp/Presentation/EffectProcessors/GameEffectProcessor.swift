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
    private let injector: Injector
    private let shareController: PasteboardControlling
    private let alertState: AlertState

    init(
        injector: Injector = Injector.shared,
        navState: Binding<[NavPath]>,
        alertState: AlertState,
        shareController: PasteboardControlling
    ) {
        self.injector = injector
        self._navState = navState
        self.alertState = alertState
        self.shareController = shareController
    }

    func process(_ effect: GameContractEffect) {
        switch effect {
        case is GameContractEffect.NavigationPreRound:
            // Navigation happens in LobbyEffectProcessor as we don't have roomID and playerID
            break
        case is GameContractEffect.NavigationYourCards:
            break
        default:
            alertState.presentedAlertType = .noFeature
        }
    }
}
