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
                        LazyVStack {
                            let sortedPlayers = viewModel.players.sorted(by: { $0.score > $1.score })
                            ForEach(sortedPlayers) { item in
                                LeaderboardRow(
                                    index: (sortedPlayers.firstIndex(
                                        where: { $0.id == item.id }
                                    ) ?? 0) + 1,
                                    nickname: item.nickname,
                                    score: item.score,
                                    isOwner: false
                                )
                            }
                        }
                        .padding(.top, .extraLarge)
                        .padding([.leading, .trailing], 50.0)
                }
                Spacer()
                PrimaryButton("Далі") {
                    viewModel.startNewRound()
                }
                .padding(.bottom, 78.0)
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
