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
    @State var answerOnTop: Bool = true

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
                        HStack {
                            Rectangle()
                                .opacity(0.001)
                                .onTapGesture {
                                    viewModel.previousAnswer()
                                    answerOnTop = true
                                }
                            Rectangle()
                                .opacity(0.001)
                                .onTapGesture {
                                    viewModel.nextAnswer()
                                    answerOnTop = true
                                }
                        }
                        ZStack(alignment: .bottom) {
                            VStack {
                                QuestionCardView(question: round.questionCard.question)
                                    .font(.cardSmall)
                                    .frame(width: 124)
                                Spacer()
                            }
                            .zIndex(answerOnTop ? 0 : 1)
                            VStack {
                                Spacer()
                                if !round.answers.isEmpty,
                                   let answer = displayedAnswer.playerAnswers.first {
                                    AnswerCardView(answer: answer.text)
                                        .font(.cardSmall)
                                        .frame(width: 124)
                                        .rotationEffect(.degrees(-8))
                                        .offset(x: -20)
                                }
                            }
                            .zIndex(answerOnTop ? 1 : 0)
                        }
                        .padding(.top, .larger)
                        .padding(.bottom, .large)
                        .onTapGesture {
                            answerOnTop.toggle()
                        }
                    }
                    RateView(viewModel.localVotes[viewModel.displayedAnswerIndex] ?? 0) { newValue in
                        viewModel.voteForCard(score: newValue)
                    }
                    .padding(.bottom, 40.0)
                    .padding([.leading, .trailing], 40.0)
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
