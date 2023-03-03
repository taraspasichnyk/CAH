//
//  LeaderboardView.swift
//  iosApp
//
//  Created by Taras Pasichnyk on 27.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct LeaderboardView<ViewModel: GameModelProtocol>: View {

    // MARK: - Properties

    @ObservedObject var viewModel: ViewModel

    // MARK: - Lifecycle

    var body: some View {
        ContainerView(header: .small) {
            VStack {
                ScrollView(.vertical) {
                    Text("Результати")
                        .padding(.top, .larger)
                        .font(.titleSemiBold)
                    if let round = viewModel.round {
                        LazyVStack {
                            ForEach(round.playerAnswers) { item in
                                LeaderboardRow(
                                    index: round.playerAnswers.firstIndex(
                                        where: { $0.player.id == item.player.id }
                                    ) ?? 0,
                                    playerRound: item
                                )
                            }
                        }
                        .padding(.top, .extraLarge)
                        .padding([.leading, .trailing], 50.0)
                    }
                }
                Spacer()
                if viewModel.player?.isOwner == true {
                    PrimaryButton("Далі") {
                        viewModel.startNewRound()
                    }
                    .padding(.bottom, 78.0)
                }
            }
        }
        .ignoresSafeArea(edges: .bottom)
    }
}

// MARK: - Previews

struct LeaderboardView_Previews: PreviewProvider {
    static var previews: some View {
        LeaderboardView(viewModel: MockGameModel(player: .mock[0]))
    }
}
