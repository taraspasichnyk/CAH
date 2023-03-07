//
//  GameModel.swift
//  iosApp
//
//  Created by Taras Pasichnyk on 27.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

protocol GameModelProtocol: ObservableObject {
    var round: GameRoundEntity? { get }
    var player: PlayerEntity? { get }
    var players: [PlayerEntity] { get }
    var selectedCard: AnswerCardEntity { get set }
    var displayedAnswerIndex: Int { get }
    var displayedAnswer: RoundPlayerAnswerEntity { get }

    func showRound()
    func saveAnswers(answerCardIds: [String])
    func voteForCard(score: Int)
    func startNewRound()
    
    func nextAnswer()
    func previousAnswer()
}

class GameModel: GameModelProtocol {

    // MARK: - Properties

    private let vm: GameViewModel

    @Published private(set) var round: GameRoundEntity? = nil
    @Published private(set) var player: PlayerEntity? = nil
    @Published private(set) var players: [PlayerEntity] = []
    @Published var selectedCard: AnswerCardEntity = .placeholder
    @Published private(set) var votes: [Int: Int] = [:]
    
    @Published private(set) var displayedAnswerIndex: Int = 0
    var displayedAnswer: RoundPlayerAnswerEntity {
        guard let round else { return .mock[0] } // TODO: Don't use mocks in prod
        return round.answers[displayedAnswerIndex]
    }

    // MARK: - Lifecycle

    init(vm: GameViewModel) {
        self.vm = vm
        subscribeToState()
    }

    // MARK: - Public

    func showRound() {
        vm.showRound()
    }

    // TODO: Move more logic inside
    func saveAnswers(answerCardIds: [String]) {
        vm.saveAnswers(answerCardIds: answerCardIds)
    }

    func startNewRound() {
        if round != nil {
            vm.showYourCards()
        } else {
            // TODO: Send an effect manually or hardcode pop from navigation stack ??
//            vm.onExit()
        }
    }

    // MARK: - Private

    private func subscribeToState() {
        AnyFlow<GameContractState>(source: vm.state).collect { [weak self] state in
            guard let self else { return }
            guard let state else { return }
            guard let player = state.me else { return }
            guard let players = state.players else { return }
            guard let round = state.round else { return }
            let answerCards = player.cards.compactMap {
                AnswerCardEntity(id: $0.id, text: $0.answer)
            }
            self.player = PlayerEntity(
                id: player.id,
                nickname: player.nickname,
                isOwner: player.isGameOwner,
                cards: answerCards,
                state: PlayerEntity.State(rawValue: player.state.name) ?? .NOT_READY
            )
            let playerEntities = players.compactMap {
                PlayerEntity(
                    id: $0.id,
                    nickname: $0.nickname,
                    isOwner: $0.isGameOwner,
                    cards: $0.cards.compactMap { AnswerCardEntity(id: $0.id, text: $0.answer) },
                    state: PlayerEntity.State(rawValue: $0.state.name) ?? .NOT_READY
                )
            }
            self.players = playerEntities
            let roundAnswers = round.answers.compactMap { playerCards in
                RoundPlayerAnswerEntity(
                    player: playerEntities.first(where: { $0.id == playerCards.playerID }) ?? PlayerEntity.mock[0],
                    playerAnswers: playerCards.playerAnswers.map({
                        AnswerCardEntity(id: $0.id, text: $0.answer, isUsed: $0.isUsed)
                    }),
                    score: Int(playerCards.score)
                )
            }
            print("❤️ --- Votes ----")
            print("\n")
            print("ID: \(round.masterCard.id), Question: \(round.masterCard.text)")
            print("\n")
            for i in roundAnswers {
                print("ID: \(i.id), Score: \(i.score), Answer: \(i.playerAnswers.first?.text ?? "Empty")")
                print("\n")
            }
            print("\n")
            print("❤️ --- End ----")
            self.round = GameRoundEntity(
                id: round.id,
                number: Int(round.number),
                questionCard: QuestionCardEntity(
                    id: round.masterCard.id,
                    text: round.masterCard.text,
                    question: round.masterCard.question,
                    gaps: round.masterCard.gaps.compactMap { NSNumber(nonretainedObject: $0) }
                ),
                answers: roundAnswers,
                state: GameRoundEntity.State(rawValue: round.state.name) ?? .FINISHED
            )
            if self.displayedAnswerIndex > roundAnswers.endIndex {
                self.displayedAnswerIndex = 0
            }
            if self.selectedCard == .placeholder {
                if answerCards.isEmpty { return }
                self.selectedCard = answerCards[0]
            }
        } onCompletion: { _ in
        }
    }
    
    // MARK: - Voting
    
    func nextAnswer() {
        if let roundAnswers = round?.answers {
            let newValue = displayedAnswerIndex + 1
            if newValue >= roundAnswers.count { return }
            displayedAnswerIndex = newValue
        }
    }
    
    func previousAnswer() {
        let newValue = displayedAnswerIndex - 1
        if newValue < 0 { return }
        displayedAnswerIndex = newValue
    }
    
    func voteForCard(score: Int) {
        // FIXME: This is bad, should be done inside shared KMM GameViewModel
        // Should be something like vm.vote(cardId: ..., score: ...)
        guard let round else { return }
        guard round.answers.indices.contains(displayedAnswerIndex) else { return }

        let answerEntity = round.answers[displayedAnswerIndex]
        let playerAnswers = answerEntity.playerAnswers.map {
            AnswerCard(id: $0.id, answer: $0.text, isUsed: $0.isUsed)
        }

        let answerCard = RoundPlayerAnswer(
            playerID: answerEntity.player.id,
            playerAnswers: playerAnswers,
            score: Int32(score)
        )

        vm.saveScores(answerCardWithVotes: [answerCard])
    }
}

// MARK: - Mock

class MockGameModel: GameModelProtocol {

    // MARK: - Properties

    @Published private(set) var round: GameRoundEntity?
    @Published private(set) var player: PlayerEntity?
    @Published private(set) var players: [PlayerEntity]
    @Published var selectedCard: AnswerCardEntity = .placeholder
    @Published private(set) var displayedAnswerIndex: Int = 0
    @Published private(set) var displayedAnswer: RoundPlayerAnswerEntity = .mock[0]

    // MARK: - Ligecycle
    init(
        round: GameRoundEntity? = GameRoundEntity.mock,
        player: PlayerEntity? = PlayerEntity.mock.first,
        players: [PlayerEntity] = PlayerEntity.mock,
        displayedAnswerIndex: Int = 0,
        displayedAnswer: RoundPlayerAnswerEntity = .mock[0]
    ) {
        self.round = round
        self.player = player
        self.players = players
        self.displayedAnswerIndex = displayedAnswerIndex
        self.displayedAnswer = displayedAnswer
    }

    // MARK: - Public

    func showRound() {
        // TODO
    }

    func saveAnswers(answerCardIds: [String]) {
        // TODO
    }

    func voteForCard(score: Int) {
        // TODO
    }

    func startNewRound() {
        // TODO
    }
    
    func nextAnswer() {
        displayedAnswerIndex = 1
        displayedAnswer = .mock[1]
    }
    
    func previousAnswer() {
        displayedAnswerIndex = 0
        displayedAnswer = .mock[0]
    }
}
