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

    func showRound()
    func saveAnswers(answerCardIds: [String])
}

class GameModel: GameModelProtocol {

    // MARK: - Properties

    private let vm: GameViewModel

    @Published private(set) var round: GameRoundEntity? = nil
    @Published private(set) var player: PlayerEntity? = nil
    @Published private(set) var players: [PlayerEntity] = []

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

    // MARK: - Private

    private func subscribeToState() {
        AnyFlow<GameContractState>(source: vm.state).collect { [weak self] state in
            guard let state else { return }
            guard let player = state.me else { return }
            guard let players = state.players else { return }
            guard let round = state.round else { return }
            self?.player = PlayerEntity(
                id: player.id,
                nickname: player.nickname,
                isOwner: player.gameOwner,
                cards: player.cards.compactMap { AnswerCardEntity(id: $0.id, text: $0.answer) },
                state: PlayerEntity.State(rawValue: player.state.name) ?? .NOT_READY
            )
            let playerEntities = players.compactMap {
                PlayerEntity(
                    id: $0.id,
                    nickname: $0.nickname,
                    isOwner: $0.gameOwner,
                    cards: $0.cards.compactMap { AnswerCardEntity(id: $0.id, text: $0.answer) },
                    state: PlayerEntity.State(rawValue: $0.state.name) ?? .NOT_READY
                )
            }
            self?.players = playerEntities
            self?.round = GameRoundEntity(
                id: round.id,
                number: Int(round.number),
                questionCard: QuestionCardEntity(
                    id: round.masterCard.id,
                    text: round.masterCard.text,
                    question: round.masterCard.question,
                    gaps: round.masterCard.gaps.compactMap { NSNumber(nonretainedObject: $0) }
                ),
                playerCards: round.playerCards.compactMap { playerCards in
                    RoundPlayerAnswerEntity(
                        player: playerEntities.first(where: { $0.id == playerCards.playerID }) ?? PlayerEntity.mock[0],
                        playerAnswers: playerCards.playerAnswers,
                        score: Int(playerCards.score)
                    )
                },
                state: GameRoundEntity.State(rawValue: round.state.name) ?? .FINISHED
            )
        } onCompletion: { _ in
        }
    }
}

// MARK: - Mock

class MockGameModel: GameModelProtocol {

    // MARK: - Properties

    @Published private(set) var round: GameRoundEntity? = GameRoundEntity.mock
    @Published private(set) var player: PlayerEntity? = PlayerEntity.mock.first
    @Published private(set) var players: [PlayerEntity] = PlayerEntity.mock

    // MARK: - Public

    func showRound() {
        // TODO
    }

    func saveAnswers(answerCardIds: [String]) {
        // TODO
    }
}
