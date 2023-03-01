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

    @ObservedObject var gameModel: ViewModel

    // MARK: - Lifecycle

    var body: some View {
        ContainerView(header: .small) {
            ZStack {
                ScrollView(.vertical) {
                    Text("Результати")
                        .font(.titleRegularSecondary)
                    if let round = gameModel.round {
                        LazyVStack {
                            ForEach(round.playerCards) { item in
                                LeaderboardRow(
                                    index: round.playerCards.firstIndex(
                                        where: { $0.player.id == item.player.id }
                                    ) ?? 0,
                                    playerRound: item
                                )
                            }
                        }
                    }
                }
                VStack {
                    Spacer()
                    HStack {
                        Spacer()
                        PrimaryButton("Далі") {
                            // TODO
                        }
                    }
                    .padding(.trailing, 20.0)
                }
            }
            Spacer()
        }
        .ignoresSafeArea()
    }
}

struct LeaderboardView_Previews: PreviewProvider {
    static var previews: some View {
        LeaderboardView(gameModel: MockGameModel())
    }
}
