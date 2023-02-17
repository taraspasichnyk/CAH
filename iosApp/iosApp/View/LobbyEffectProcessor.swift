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
    private let alert: AlertState

    init(
        injector: Injector = Injector.shared,
        navState: Binding<[NavPath]>,
        shareController: PasteboardControlling,
        alert: AlertState
    ) {
        self.injector = injector
        self._navState = navState
        self.shareController = shareController
        self.alert = alert
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
        case is LobbyContractEffect.NavigationYourCardsScreen:
            // TODO: navState.navigate(to: .yourCards)
            alert.isPresentingNoFeature = true
        case let copyCodeEffect as LobbyContractEffect.CopyCode:
            shareController.copyToPasteboard(copyCodeEffect.code)
        case let errorEffect as LobbyContractEffect.ShowError:
            alert.errorMessage = errorEffect.message
        default:
            alert.isPresentingNoFeature = true
        }
    }
}
