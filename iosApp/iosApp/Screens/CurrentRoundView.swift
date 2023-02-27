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
    @State private var selectedIndex = 0

    @State private var currentRound: GameRound?
    @State private var question: String = ""
    @State private var answers: [AnswerCard] = []
    @State private var selectedAnswers: [AnswerCard] = []

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
                        }
                        Spacer()
                        Spacer()
                        Text("Оберіть 2 відповіді:")
                            .font(.bodyTertiaryThin)
                        CardHandPicker(
                            selectedIndex: $selectedIndex,
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
            DispatchQueue.main.asyncAfter(deadline: .now() + .seconds(1)) {
                withAnimation(.easeInOut(duration: 0.5)) {
                    hasLoaded = true
                }
            }
            DispatchQueue.main.asyncAfter(deadline: .now() + .milliseconds(1500)) {
                hasShiftedTitle = true
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
                    vm.saveAnswers(answerCardIds: selectedAnswers.map(\.id))
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
        .transition(.opacity)
    }
}

// MARK: - Private

extension CurrentRoundView {
    private func subscribeToState() {
// TODO: Explore using substates
//        AnyFlow<Player>(source: vm.me).collect { player in
//            guard let player else { return }
//        }

        AnyFlow<GameContractState>(source: vm.state).collect { state in
            guard let state else { return }
            guard let room = state.room else { return }
            answers = room.answers
            currentRound = room.currentRound
            question = room.masterCard.text
        } onCompletion: { error in
            guard let error else { return }
            print(error)
        }
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
