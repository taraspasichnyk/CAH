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

    // MARK: - Lifecycle

    var body: some View {
        ContainerView(header: .small) {
            if let round = viewModel.round,
               let displayedAnswer = viewModel.displayedAnswer{
                VStack {
                    Text("Раунд \(round.number) - Голосування")
                        .padding(.top, .larger)
                        .font(.titleSemiBold)
                    SelectorView(viewModel.displayedAnswerIndex, count: round.answers.count)
                        .padding(.top, .medium)
                        .padding([.leading, .trailing], .medium)
                    ZStack {
                        ZStack(alignment: .bottom) {
                            VStack {
                                QuestionCardView(question: round.questionCard.question)
                                    .frame(minWidth: 124, maxWidth: 180)
                                    .font(.cardSmall)
                                Spacer()
                            }
                            VStack {
                                Spacer()
                                if !round.answers.isEmpty,
                                   let answer = displayedAnswer.playerAnswers.first {
                                    AnswerCardView(answer: answer.text)
                                        .font(.cardSmall)
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
                                    viewModel.previousAnswer()
                                }
                            Rectangle()
                                .opacity(0.001)
                                .onTapGesture {
                                    viewModel.nextAnswer()
                                }
                        }
                    }
                    RateView(viewModel.localVotes[viewModel.displayedAnswerIndex] ?? 0) { newValue in
                        viewModel.voteForCard(score: newValue)
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
