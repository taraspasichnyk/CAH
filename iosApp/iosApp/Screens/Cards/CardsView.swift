//
//  CardsView.swift
//  iosApp
//
//  Created by Taras Pasichnyk on 21.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct CardsView<ViewModel>: View where ViewModel: GameModelProtocol {

    // MARK: - Properties

    @ObservedObject var gameModel: ViewModel

    private let columns = [
        GridItem(spacing: 8.0),
        GridItem(spacing: 8.0),
        GridItem(spacing: 8.0)
    ]

    private let spacing = 8.0

    // MARK: - Lifecycle

    var body: some View {
        ContainerView(header: .small) {
            ZStack {
                ScrollView(.vertical) {
                    Text("Ваші карти")
                        .font(.titleRegularSecondary)
                    LazyVGrid(
                        columns: columns,
                        alignment: .center,
                        spacing: spacing
                    ) {
                        if let player = gameModel.player {
                            ForEach(gameModel.items, id: \.self) { item in
                                AnswerCardView(answer: item.text)
                                    .font(.cardSmall)
                            }
                        }
                    }
                    .padding([.leading, .trailing], 20.0)
                }
                VStack {
                    Spacer()
                    HStack {
                        Spacer()
                        PrimaryButton("Далі") {
                            gameModel.showRound()
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

// MARK: - Previews

struct CardsView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            CardsView(gameModel: MockGameModel())
        }
    }
}
