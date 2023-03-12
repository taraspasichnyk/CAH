//
//  GameScreen.swift
//  iosApp
//
//  Created by Artem Yelizarov on 01.03.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct GameScreen<ViewModel: GameModelProtocol>: View {

    let viewModel: ViewModel
    @Binding private(set) var gameNavState: GameNavState

    // MARK: - Body

    var body: some View {
        Group {
            switch gameNavState {
            case .yourCards:
                CardsView(viewModel: viewModel)
            case .preround:
                CurrentRoundView(viewModel: viewModel)
            case .voting:
                VoteView(viewModel: viewModel.makeVoteViewModel())
            case .leaderBoard:
                LeaderboardView(viewModel: viewModel)
            }
        }
    }
}

// MARK: - Previews

struct GameScreen_Previews: PreviewProvider {
    static var previews: some View {
        GameScreen(viewModel: MockGameModel(), gameNavState: .constant(.yourCards))
    }
}
