//
//  CurrentRoundView.swift
//  iosApp
//
//  Created by Artem Yelizarov on 21.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct CurrentRoundView<ViewModel: GameModelProtocol>: View {
    @StateObject var viewModel: ViewModel

    @State private var hasLoaded = false
    @State private var hasShiftedTitle = false
    @State private var hasFullScreenCards = false

    /// This card is chosen as the answer to the question
    @State private var confirmedCard: AnswerCardEntity?

    // TODO: Move more logic inside new model, check if binding needed here
    private var cardsInHand: Binding<[AnswerCardEntity]> {
        Binding {
            guard let player = viewModel.player else {
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
        if case let .some(round) = viewModel.round {
            ZStack {
                if hasFullScreenCards {
                    FullScreenHandView(cards: viewModel.player?.cards ?? [])
                } else {
                    ContainerView(header: .small) {
                        VStack {
                            Spacer()
                            Text("Раунд \(round.number)")
                                .font(hasLoaded ? .titleSemiBold : .largeTitleSemiBold)
                            Spacer()
                            if hasLoaded {
                                ZStack(alignment: .bottom) {
                                    VStack {
                                        QuestionCardView(question: round.questionCard.question)
                                            .font(.inputPrimary)
                                            .shadow(radius: 8.0, y: 4.0)
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
                                .opacity(hasShiftedTitle ? 1 : 0)
                                Spacer()
                                Spacer()
                                Text("Оберіть відповідь:")
                                    .font(.bodyTertiaryThin)
                                CardHandPicker(
                                    selectedCard: $viewModel.selectedCard,
                                    answers: cardsInHand
                                )
                            }
                        }
                        .padding(.bottom, .extraLarge)
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
                .opacity(hasFullScreenCards ? 0 : 1)
                Spacer()
            }
            .padding(.bottom, 35)
            .overlay(
                HStack {
                    Spacer()
                    GridButton {
                        hasFullScreenCards.toggle()
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
        guard let answers = viewModel.player?.cards else { return }
        var selectedIndex = answers.firstIndex(of: viewModel.selectedCard) ?? 0
        if selectedIndex > 0 {
            selectedIndex -= 1
        }

        confirmedCard = viewModel.selectedCard
        viewModel.saveAnswers(answerCardIds: [viewModel.selectedCard.id])
        viewModel.selectedCard = answers[selectedIndex]
    }
}

// MARK: - Previews

struct CurrentRoundView_Previews: PreviewProvider {
    static var previews: some View {
        CurrentRoundView(viewModel: MockGameModel())
    }
}
