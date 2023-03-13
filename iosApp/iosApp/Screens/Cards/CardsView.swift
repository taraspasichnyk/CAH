//
//  CardsView.swift
//  iosApp
//
//  Created by Taras Pasichnyk on 21.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct CardsView<ViewModel: GameModelProtocol>: View {

    // MARK: - Properties

    @ObservedObject var viewModel: ViewModel

    @State private var answerCards: [AnswerCardEntity] = []
    @State private var deckCards: [AnswerCardEntity] = []
    @State private var areAnswerCardsShowed = false
    @Namespace private var answerCardsNamespace

    private let columns = [
        GridItem(spacing: 8.0),
        GridItem(spacing: 8.0),
        GridItem(spacing: 8.0)
    ]
    private let spacing = 8.0
    private let animationDuration = 5.0

    // MARK: - Lifecycle

    var body: some View {
        ZStack {
            ContainerView(header: .small) {
                ZStack {
                    ScrollViewReader { scrollReader in
                        ScrollView(showsIndicators: false) {
                            Text("Ваші карти")
                                .font(.titleRegularSecondary)
                            LazyVGrid(
                                columns: columns,
                                spacing: spacing
                            ) {
                                ForEach(answerCards) {
                                    AnswerCardView(answer: $0.text)
                                        .font(.cardSmall)
                                        .id($0.id)
                                        .matchedGeometryEffect(id: $0.id, in: answerCardsNamespace)
                                }.onChange(of: answerCards.count) { cardsCount in
                                    withAnimation {
                                        scrollReader.scrollTo(answerCards[cardsCount - 1].id)
                                    }
                                }
                            }.padding([.leading, .trailing], 20)
                        }
                    }
                    if areAnswerCardsShowed {
                        VStack {
                            Spacer()
                            HStack {
                                Spacer()
                                PrimaryButton("Далі") {
                                    viewModel.showRound()
                                }
                            }
                            .padding(.trailing, 20)
                        }
                    }
                }
                Spacer()
            }
            .ignoresSafeArea()
            if !deckCards.isEmpty {
                GeometryReader { geometry in
                    let viewWidth = geometry.size.width
                    let deckWidth = deckWidth(viewWidth: viewWidth)
                    let deckHeight = deckWidth / Size.answerCardAspectRatio.rawValue
                    ZStack {
                        ForEach(deckCards) {
                            AnswerCardView(answer: $0.text)
                                .font(.cardSmall)
                                .matchedGeometryEffect(id: $0.id, in: answerCardsNamespace)
                        }
                        Image.bgTexture
                            .background { Color.white }
                    }
                    .frame(width: deckWidth)
                    .clipShape(RoundedRectangle(cornerRadius: 8))
                    .shadow(radius: 8)
                    .offset(x: viewWidth - deckWidth - 20,
                            y: geometry.size.height - deckHeight)
                }
                .ignoresSafeArea()
            }
        }
        .onChange(of: viewModel.player, perform: { player in
            if answerCards.isEmpty && deckCards.isEmpty {
                deckCards = player?.cards ?? []
                showAnswerCards()
            }
        })
    }

    private func deckWidth(viewWidth: Double) -> Double {
        let spacingsWidth = spacing * Double(columns.count + 1)
        return (viewWidth - spacingsWidth) / Double(columns.count)
    }

    private func showAnswerCards() {
        let initialAnimationDelay = animationDuration / Double(deckCards.count)
        let animationDelay = initialAnimationDelay / 2

        for (deckIndex, deckCard) in deckCards.enumerated() {
            let delay = animationDelay + (animationDelay * Double(deckIndex))
            DispatchQueue.main.asyncAfter(deadline: .now() + delay) {
                withAnimation(.easeOut(duration: initialAnimationDelay)) {
                    answerCards.append(deckCard)
                    deckCards.removeElement(deckCard)
                }
            }
        }
        DispatchQueue.main.asyncAfter(
            deadline: .now() + animationDelay * Double(deckCards.count)
            + initialAnimationDelay) {
                withAnimation(.easeIn(duration: 1)) {
                    areAnswerCardsShowed = true
                }
        }
    }
}

// MARK: - Previews

struct CardsView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            CardsView(viewModel: MockGameModel())
        }
    }
}
