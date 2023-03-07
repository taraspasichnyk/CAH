//
//  VoteView.swift
//  iosApp
//
//  Created by Taras Pasichnyk on 01.03.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct VoteView<ViewModel: GameModelProtocol>: View {
    
    // MARK: - Properties

    @StateObject var viewModel: ViewModel

    @State var displayedCardIndex: Int = 0
    @State var selectedRateValue: Int = 0

    // MARK: - Lifecycle

    var body: some View {
        ContainerView(header: .small) {
            if let round = viewModel.round {
                VStack {
                    Text("Раунд \(round.number) - Голосування")
                        .padding(.top, .larger)
                        .font(.titleSemiBold)
                    SelectorView($displayedCardIndex, count: round.answers.count)
                        .padding(.top, .medium)
                        .padding([.leading, .trailing], .medium)
                    ZStack {
                        ZStack(alignment: .bottom) {
                            VStack {
                                QuestionCardView(question: round.questionCard.question)
                                    .frame(minWidth: 124, maxWidth: 180)
                                Spacer()
                            }
                            VStack {
                                Spacer()
                                if let answer = round.answers[displayedCardIndex].playerAnswers.first {
                                    AnswerCardView(answer: answer.text)
                                        .font(.inputPrimary)
                                        .frame(minWidth: 124, maxWidth: 180)
                                        .rotationEffect(.degrees(-8))
                                        .offset(x: -20)
                                }
                            }
                        }
                        .padding(.top, .larger)
                        .padding(.bottom, .large)
                        Spacer()
                        HStack {
                            Rectangle()
                                .opacity(0.001)
                                .onTapGesture {
                                    // TODO: Pack logic into VM
                                    if displayedCardIndex > 0 {
                                        displayedCardIndex-=1
                                    }
                                }
                            Rectangle()
                                .opacity(0.001)
                                .onTapGesture {
                                    // TODO: Pack logic into VM
                                    if displayedCardIndex < (viewModel.round?.answers.count ?? 0) - 1 {
                                        displayedCardIndex+=1
                                    }
                                }
                        }
                    }
                    RateView($selectedRateValue) { newValue in
                        selectedRateValue = newValue
                        viewModel.voteForCard(at: displayedCardIndex, score: selectedRateValue)
                    }
                    .padding(.bottom, 40.0)
                }
            }
        }
        .ignoresSafeArea(edges: .bottom)
    }
}

// MARK: - Previews

struct VoteView_Previews: PreviewProvider {
    static var previews: some View {
        VoteView(viewModel: MockGameModel(player: .mock[0]))
    }
}
