//
//  GameScreen.swift
//  iosApp
//
//  Created by Artem Yelizarov on 01.03.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct GameScreen<ViewModel: GameModelProtocol>: View {

    let gameModel: ViewModel
    @Binding private(set) var gameNavState: GameNavState

    // MARK: - Body

    var body: some View {
        Group {
            switch gameNavState {
            case .yourCards:
                CardsView(gameModel: gameModel)
            case .preround:
                CurrentRoundView(gameModel: gameModel)
            case .voting:
                EmptyView()
            }
        }
    }
}

// MARK: - Previews

struct GameScreen_Previews: PreviewProvider {
    static var previews: some View {
        GameScreen(gameModel: MockGameModel(), gameNavState: .constant(.yourCards))
    }
}
