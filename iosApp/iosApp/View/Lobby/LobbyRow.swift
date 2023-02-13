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
    @Binding var user: UserInLobby

    // MARK: - Body

    var body: some View {
        VStack(alignment: .center, spacing: 6) {
            HStack(alignment: .center) {
                Text("1. " + user.name)
                    .font(.bodySecondary)
                if user.isGameOwner {
                    Image.host
                        .frame(width: .large, height: .large)
                }
                Spacer()
                Text(user.isReadyToPlay ? "Готовий" : "Очікує")
                    .font(.bodyTertiary)
                    .foregroundColor(.white)
                    .padding(.vertical, 6)
                    .frame(width: 76)
                    .background(user.isReadyToPlay ? Color.readyGreen : .pendingYellow)
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
            user: .constant(
                .init(
                    name: "Гриць",
                    isGameOwner: true,
                    isReadyToPlay: true
                )
            )
        )
    }
}
