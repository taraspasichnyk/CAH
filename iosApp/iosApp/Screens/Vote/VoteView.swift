//
//  VoteView.swift
//  iosApp
//
//  Created by Taras Pasichnyk on 01.03.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct VoteView<ViewModel: VoteViewModelProtocol>: View {
    
    // MARK: - Properties

    @StateObject var viewModel: ViewModel
    @State var answerOnTop: Bool = true
    @State var isOut: Bool = true

    // MARK: - Lifecycle

    var body: some View {
        ContainerView(header: .small) {
            VStack {
                Text("Раунд \(viewModel.roundNumber) - Голосування")
                    .padding(.top, .larger)
                    .font(.titleSemiBold)
                SelectorView(viewModel.displayedAnswerIndex, count: viewModel.answers.count)
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
                            QuestionCardView(question: viewModel.question)
                                .font(.cardSmall)
                                .frame(width: 124)
                                .offset(
                                    x: isOut ? 0 : 20,
                                    y: isOut ? 0 : -20
                                )
                            Spacer()
                        }
                        .zIndex(answerOnTop ? 0 : 1)
                        VStack {
                            Spacer()
                            if let answer = viewModel.displayedAnswer.playerAnswers.first {
                                ZStack(alignment: .bottom) {
                                    AnswerCardView(answer: answer.text)
                                        .font(.cardSmall)
                                        .frame(width: 124)
                                    if let value = RateView.Value(rawValue: viewModel.displayedAnswer.score) {
                                        value.image.resizable()
                                            .aspectRatio(1.0, contentMode: .fit)
                                            .frame(width: 52)
                                            .padding(.bottom, .large)
                                    }
                                }
                                .rotationEffect(.degrees(-8))
                                .offset(
                                    x: isOut ? -20 : -40,
                                    y: isOut ? 0 : 20
                                )
                            }
                        }
                        .zIndex(answerOnTop ? 1 : 0)
                    }
                    .padding(.top, .larger)
                    .padding(.bottom, .large)
                    .onTapGesture {
                        let duration = 0.14
                        withAnimation(.easeIn(duration: duration)) {
                            isOut.toggle()
                        }
                        DispatchQueue.main.asyncAfter(deadline: .now() + duration) {
                            withAnimation(.easeOut(duration: duration)) {
                                answerOnTop.toggle()
                            }
                            DispatchQueue.main.asyncAfter(deadline: .now() + duration) {
                                withAnimation(.easeOut(duration: duration)) {
                                    isOut.toggle()
                                }
                            }
                        }
                    }
                }
                RateView(viewModel.displayedAnswer.score) { newValue in
                    viewModel.voteForCard(score: newValue)
                }
                .padding(.bottom, 40.0)
                .padding([.leading, .trailing], 40.0)
            }
        }
        .ignoresSafeArea(edges: .bottom)
    }
}

// MARK: - Previews

struct VoteView_Previews: PreviewProvider {
    static var previews: some View {
        VoteView(viewModel: MockVoteViewModel())
    }
}
