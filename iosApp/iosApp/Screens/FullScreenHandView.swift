//
//  FullScreenHandView.swift
//  iosApp
//
//  Created by Artem Yelizarov on 01.03.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct FullScreenHandView: View {
    let cards: [AnswerCardEntity]

    private let spacing = 8.0

    // MARK: - Body

    var body: some View {
        VStack {
            ScrollView(.vertical) {
                LazyVGrid(
                    columns: [
                        GridItem(spacing: spacing),
                        GridItem(spacing: spacing),
                        GridItem(spacing: spacing),
                    ],
                    alignment: .center,
                    spacing: spacing
                ) {
                    ForEach(cards, id: \.self) { item in
                        AnswerCardView(answer: item.text)
                            .font(.cardSmall)
                    }
                }
                .padding([.leading, .trailing], 20.0)
            }
            .content.offset(y: HeaderSize.small.height + 12)
            .ignoresSafeArea(.all)
            Spacer()
        }
        VStack {
            HeaderView(size: .small)
                .fixedSize(horizontal: false, vertical: true)
                .ignoresSafeArea(.all)
            Spacer()
        }
        .ignoresSafeArea(.all)
        .navigationBarBackButtonHidden(true)
    }
}

// MARK: - Previews

struct FullSreenHandView_Previews: PreviewProvider {
    static var previews: some View {
        ZStack {
            FullScreenHandView(cards: AnswerCardEntity.mock)
        }
    }
}
