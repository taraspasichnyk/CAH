//
//  VoteViewModel.swift
//  iosApp
//
//  Created by Artem Yelizarov on 09.03.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

protocol VoteViewModelProtocol: ObservableObject {
    var roundNumber: Int { get }
    var question: String { get }
    var answers: [RoundPlayerAnswerEntity] { get }
    var displayedAnswerIndex: Int { get }
    var displayedAnswer: RoundPlayerAnswerEntity { get }

    func previousAnswer()
    func nextAnswer()
    func voteForCard(score: Int)
}

// MARK: - VoteViewModel

final class VoteViewModel: ObservableObject, VoteViewModelProtocol {
    
    @Published private(set) var answers: [RoundPlayerAnswerEntity]
    @Published private(set) var displayedAnswerIndex: Int = 0

    let roundNumber: Int
    let question: String

    var displayedAnswer: RoundPlayerAnswerEntity {
        answers[displayedAnswerIndex]
    }

    private let vm: VoteGameViewModelProtocol

    init(
        vm: VoteGameViewModelProtocol,
        round: GameRoundEntity?
    ) {
        self.vm = vm
        self.roundNumber = round?.number ?? 0
        self.question = round?.questionCard.question ?? ""
        self.answers = round?.answers ?? []
    }

    // MARK: - Voting

    func nextAnswer() {
        let newValue = displayedAnswerIndex + 1
        guard newValue < answers.count else { return }
        displayedAnswerIndex = newValue
    }

    func previousAnswer() {
        guard displayedAnswerIndex > 0 else { return }
        displayedAnswerIndex -= 1
    }

    func voteForCard(score: Int) {
        // FIXME: This is bad, should be done inside shared KMM GameViewModel
        // Should be something like vm.vote(cardId: ..., score: ...)
        guard answers.indices.contains(displayedAnswerIndex) else { return }

        answers[displayedAnswerIndex].score = score

        let answerCards = answers.map {
            RoundPlayerAnswer(
                playerID: $0.playerId,
                playerAnswers: $0.playerAnswers.map {
                    AnswerCard(id: $0.id, answer: $0.text, isUsed: $0.isUsed)
                },
                score: Int32($0.score)
            )
        }

        vm.saveScores(answerCardWithVotes: answerCards)
    }
}

// MARK: - Mocks

final class MockVoteViewModel: VoteViewModelProtocol {
    var roundNumber: Int = 1
    var question: String = "Я люблю свою роботу і ____"
    var answers: [RoundPlayerAnswerEntity] = []
    var displayedAnswerIndex = 0
    var displayedAnswer: RoundPlayerAnswerEntity = .mock[0]

    func previousAnswer() {
        // TODO
    }

    func nextAnswer() {
        // TODO
    }

    func voteForCard(score: Int) {
        // TODO
    }
}
