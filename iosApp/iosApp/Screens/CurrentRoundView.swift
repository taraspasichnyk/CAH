//
//  CurrentRoundView.swift
//  iosApp
//
//  Created by Artem Yelizarov on 21.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct CurrentRoundView: View {
    @State private var hasLoaded = false
    @State private var hasShiftedTitle = false

    @State private var currentRound: shared.GameRound?
    @State private var question: String = ""
    @State private var selectedCard: CardItem = .placeholder
    @State private var answers: [CardItem] = [.placeholder]

    let vm: GameViewModel

    // MARK: - Body

    var body: some View {
        ZStack {
            ContainerView(header: .small) {
                VStack {
                    Spacer()
                    Text("Раунд \(currentRound?.number ?? 0)")
                        .font(hasLoaded ? .titleSemiBold : .largeTitleSemiBold)
                    Spacer()
                    if hasLoaded {
                        if hasShiftedTitle {
                            QuestionCardView(question: question)
                        } else {
                            Spacer()
                            Spacer()
                        }
                        Spacer()
                        Spacer()
                        Text("Оберіть 1 відповідь:")
                            .font(.bodyTertiaryThin)
                            CardHandPicker(
                                selectedCard: $selectedCard,
                                answers: answers
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
            subscribeToState()
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
    private func subscribeToState() {
        AnyFlow<GameContractState>(source: vm.state).collect { state in
            guard let state else { return }
            guard let player = state.me else { return }
            guard let round = state.round else { return }
            currentRound = round
            question = round.masterCard.question
            answers = player.cards.map {
                CardItem(id: $0.id, text: $0.answer)
            }
        } onCompletion: { _ in
        }
    }

    private func saveAnswers() {
        vm.saveAnswers(answerCardIds: [selectedCard.id])
    }
}

// MARK: - Previews

struct CurrentRoundView_Previews: PreviewProvider {
    static var previews: some View {
        CurrentRoundView(
            vm: .init(roomId: "484172", playerId: "-NOxn4NgUTxDzyRpcA0J")
        )
    }
}
