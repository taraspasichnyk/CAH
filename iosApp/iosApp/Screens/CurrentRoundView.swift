//
//  CurrentRoundView.swift
//  iosApp
//
//  Created by Artem Yelizarov on 21.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct CurrentRoundView<ViewModel: GameModelProtocol>: View {
    @ObservedObject private(set) var gameModel: ViewModel

    @State private var hasLoaded = false
    @State private var hasShiftedTitle = false

    /// This card is chosen as the answer to the question
    @State private var confirmedCard: AnswerCardEntity?

    // TODO: Move more logic inside new model, check if binding needed here
    private var cardsInHand: Binding<[AnswerCardEntity]> {
        Binding {
            guard let player = gameModel.player else {
                return []
            }
            return player.cards.compactMap {
                $0 == confirmedCard ? nil : $0
            }
        } set: { value, _ in
        }
    }

    // MARK: - Body

    var body: some View {
        // FIXME: Not cool, this check probably should be at least one level up
        if case let .some(round) = gameModel.round {
            ZStack {
                ContainerView(header: .small) {
                    VStack {
                        Spacer()
                        Text("Раунд \(round.number)")
                            .font(hasLoaded ? .titleSemiBold : .largeTitleSemiBold)
                        Spacer()
                        if hasLoaded {
                            if hasShiftedTitle {
                                ZStack(alignment: .bottom) {
                                    VStack {
                                        QuestionCardView(question: round.questionCard.question)
                                        Spacer()
                                    }
                                    if let confirmedCard {
                                        VStack {
                                            Spacer()
                                            AnswerCardView(answer: confirmedCard.text)
                                                .font(.cardSmall)
                                                .frame(width: 124, height: 168)
                                                .rotationEffect(.degrees(-5))
                                                .offset(x: -62)
                                        }
                                    }
                                }
                            } else {
                                Spacer(minLength: 200)
                            }
                            Spacer()
                            Spacer()
                            Text("Оберіть відповідь:")
                                .font(.bodyTertiaryThin)
                            CardHandPicker(
                                selectedCard: $gameModel.selectedCard,
                                answers: cardsInHand
                            )
                            .padding(.bottom, .large)
                        }
                    }
                }
                if hasLoaded {
                    bottomButtonStack
                }
            }
            .onAppear {
                DispatchQueue.main.asyncAfter(deadline: .now() + .seconds(1)) {
                    withAnimation(.easeInOut(duration: 0.5)) {
                        hasLoaded = true
                    }
                }
                DispatchQueue.main.asyncAfter(deadline: .now() + .milliseconds(1500)) {
                    withAnimation {
                        hasShiftedTitle = true
                    }
                }
            }
        } else {
            EmptyView()
        }
    }
}

// MARK: - Subviews

extension CurrentRoundView {
    private var bottomButtonStack: some View {
        return VStack {
            Spacer()
            HStack {
                Spacer()
                PrimaryButton("Обрати") {
                    saveAnswers()
                }
                Spacer()
            }
            .padding(.bottom, 35)
            .overlay(
                HStack {
                    Spacer()
                    GridButton {

                        print("TOGGLE CARD VIEW")
                    }
                    .padding([.bottom, .trailing], 35)
                }
            )
        }
        .ignoresSafeArea()
        .contentTransition(.opacity)
        .transition(.opacity)
    }
}

// MARK: - Private

extension CurrentRoundView {
    private func saveAnswers() {
        guard let answers = gameModel.player?.cards else { return }
        var selectedIndex = answers.firstIndex(of: gameModel.selectedCard) ?? 0
        if selectedIndex > 0 {
            selectedIndex -= 1
        }

        confirmedCard = gameModel.selectedCard
        gameModel.saveAnswers(answerCardIds: [gameModel.selectedCard.id])
        gameModel.selectedCard = answers[selectedIndex]
    }
}

// MARK: - Previews

struct CurrentRoundView_Previews: PreviewProvider {
    static var previews: some View {
        CurrentRoundView(gameModel: MockGameModel())
    }
}
