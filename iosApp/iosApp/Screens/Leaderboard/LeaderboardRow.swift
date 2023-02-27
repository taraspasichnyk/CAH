//
//  LeaderboardRow.swift
//  iosApp
//
//  Created by Taras Pasichnyk on 27.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct LeaderboardRow: View {

    let index: Int
    let playerRound: RoundPlayerAnswerEntity

    var body: some View {
        VStack(alignment: .center, spacing: 6) {
            HStack(alignment: .center) {
                Text("\(index). " + playerRound.player.nickname)
                    .font(.bodySecondary)
                if playerRound.player.isOwner {
                    Image.host
                        .frame(width: .large, height: .large)
                }
                Spacer()
                Text(String(playerRound.score))
            }
            .padding(.horizontal, 4)
            Color.mainBlack
                .frame(height: 1)
        }
    }
}

struct LeaderboardRow_Previews: PreviewProvider {
    static var previews: some View {
        LazyVStack {
            LeaderboardRow(index: 0, playerRound: RoundPlayerAnswerEntity.mock[0])
            LeaderboardRow(index: 1, playerRound: RoundPlayerAnswerEntity.mock[1])
        }
    }
}
