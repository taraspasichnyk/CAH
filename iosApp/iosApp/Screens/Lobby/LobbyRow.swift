//
//  LobbyRow.swift
//  iosApp
//
//  Created by Artem Yelizarov on 09.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LobbyRow: View {
    let offset: Int
    let user: Player

    // MARK: - Body

    var body: some View {
        VStack(alignment: .center, spacing: 6) {
            HStack(alignment: .center) {
                Text("\(offset + 1). " + user.nickname)
                    .font(.bodySecondary)
                if user.gameOwner {
                    Image.host
                        .frame(width: .large, height: .large)
                }
                Spacer()
                Text(user.state == .ready ? "Готовий" : "Очікує")
                    .font(.bodyTertiary)
                    .foregroundColor(.white)
                    .padding(.vertical, 6)
                    .frame(width: 76)
                    .background(user.state == .ready ? Color.readyGreen : .pendingYellow)
                    .cornerRadius(4)
            }
            .padding(.horizontal, 4)
            Color.mainBlack
                .frame(height: 1)
        }
    }
}

// MARK: - Previews

struct LobbyRow_Previews: PreviewProvider {
    static var previews: some View {
        LobbyRow(
            offset: 1,
            user: .init(
                id: "1",
                nickname: "Гриць",
                gameOwner: true,
                cards: [],
                score: 0,
                state: .ready
            )
        )
    }
}
