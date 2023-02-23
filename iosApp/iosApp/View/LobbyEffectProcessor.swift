//
//  LobbyEffectProcessor.swift
//  iosApp
//
//  Created by Artem Yelizarov on 17.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

final class LobbyEffectProcessor {
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

    func process(_ effect: LobbyContractEffect) {
        switch effect {
        case is LobbyContractEffect.NavigationUsersListScreen:
            if let lobbyVm = navState.compactMap(\.lobbyViewModel).last {
                navState.append(.lobby(lobbyVm))
            }
        case is LobbyContractEffect.NavigationMenuScreen:
            navState = []
        case is LobbyContractEffect.NavigationEnterCodeScreen:
            if let lobbyVm = navState.compactMap(\.lobbyViewModel).last {
                navState.navigate(to: .enterCode(lobbyVm))
            }
        case is LobbyContractEffect.NavigationEnterNameScreen:
            let lobbyVm = navState.compactMap(\.lobbyViewModel).last ?? injector.lobbyOwnerViewModel
            navState.navigate(to: .enterName(lobbyVm))
        case is LobbyContractEffect.NavigationGameScreen:
            if let gameViewModel = navState.compactMap(\.gameViewModel).last {
                navState.navigate(to: .yourCards(gameViewModel))
            }
        case let copyCodeEffect as LobbyContractEffect.CopyCode:
            shareController.copyToPasteboard(copyCodeEffect.code)
        case let errorEffect as LobbyContractEffect.ShowError:
            alertState.presentedAlertType = .error(message: errorEffect.message)
        default:
            alertState.presentedAlertType = .noFeature
        }
    }
}
