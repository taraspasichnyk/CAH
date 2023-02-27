//
//  LeaderboardView.swift
//  iosApp
//
//  Created by Taras Pasichnyk on 27.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct LeaderboardView<GameModelType>: View where GameModelType: GameModelProtocol {

    // MARK: - Properties

    @ObservedObject var gameModel: GameModelType

    // MARK: - Lifecycle

    var body: some View {
        ContainerView(header: .small) {
//            ZStack {
//                ScrollView(.vertical) {
//                    Text("Ваші карти")
//                        .font(.titleRegularSecondary)
//                    LazyVStack {
//                        ForEach(Array(users.enumerated()), id: \.element.id) {
//                            LobbyRow(offset: $0, user: $1)
//                        }
//                    }
//                }
//                VStack {
//                    Spacer()
//                    HStack {
//                        Spacer()
//                        PrimaryButton("Далі") {
//                            // TODO
//                        }
//                    }
//                    .padding(.trailing, 20.0)
//                }
//            }
//            Spacer()
        }
        .ignoresSafeArea()
    }
}

struct LeaderboardView_Previews: PreviewProvider {
    static var previews: some View {
        LeaderboardView(gameModel: MockGameModel())
    }
}
