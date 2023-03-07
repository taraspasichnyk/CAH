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
    let nickname: String
    let score: Int
    let isOwner: Bool

    var body: some View {
        VStack(alignment: .center, spacing: 6) {
            HStack(alignment: .center) {
                Text("\(index). " + nickname)
                    .font(.bodySecondary)
                if isOwner {
                    Image.host
                        .frame(width: .large, height: .large)
                }
                Spacer()
                Text(String(score))
                    .font(.bodySecondary)
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
            LeaderboardRow(index: 1, nickname: "A1sdaffdasds", score: 123, isOwner: false)
            LeaderboardRow(index: 2, nickname: "A22asdasdf", score: 1342, isOwner: true)
        }
    }
}
