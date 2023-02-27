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

    // MARK: - Private

    private func subscribeToState() {
        AnyFlow<GameContractState>(source: vm.state).collect { [weak self] state in
            guard let state else { return }
            guard let player = state.me else { return }
            guard let players = state.players else { return }
            guard let round = state.round else { return }
            self?.round = GameRoundEntity(
                id: round.id,
                number: Int(round.number),
                questionCard: QuestionCardEntity(
                    id: round.masterCard.id,
                    text: round.masterCard.text,
                    question: round.masterCard.question,
                    gaps: round.masterCard.gaps.compactMap { NSNumber(nonretainedObject: $0) }
                ),
                playerCards: round.playerCards.compactMap {
                    RoundPlayerAnswerEntity(playerId: $0.playerID, playerAnswers: $0.playerAnswers, score: Int($0.score))
                },
                state: GameRoundEntity.State(rawValue: round.state.name) ?? .FINISHED
            )
            self?.player = PlayerEntity(
                id: player.id,
                nickname: player.nickname,
                isOwner: player.gameOwner,
                cards: player.cards.compactMap { AnswerCardEntity(id: $0.id, text: $0.answer) },
                state: PlayerEntity.State(rawValue: player.state.name) ?? .NOT_READY
            )
            self?.players = players.compactMap {
                PlayerEntity(
                    id: $0.id,
                    nickname: $0.nickname,
                    isOwner: $0.gameOwner,
                    cards: $0.cards.compactMap { AnswerCardEntity(id: $0.id, text: $0.answer) },
                    state: PlayerEntity.State(rawValue: $0.state.name) ?? .NOT_READY
                )
            }
        } onCompletion: { _ in
        }
    }
}

// MARK: - Mock

class MockGameModel: GameModelProtocol {

    // MARK: - Properties

    @Published private(set) var round: GameRoundEntity? = nil
    @Published private(set) var player: PlayerEntity? = PlayerEntity(
        id: "123",
        nickname: "Artem&Taras",
        isOwner: true,
        cards: [
            .init(id: "123", text: "Степан Гіга"),
            .init(id: "123", text: "Знімати персики з дерева біля ЖЕКу"),
            .init(id: "123", text: "Місити палкою кропиву"),
            .init(id: "123", text: "Неймовірний покемон Сквіртл"),
            .init(id: "123", text: "Картонний пакет Кагору"),
            .init(id: "123", text: "Футбольний клуб \"Карпати\""),
            .init(id: "123", text: "Майнити біткойни на Atari"),
            .init(id: "123", text: "Стрілецька Дивізія \"СС Галичина\""),
            .init(id: "123", text: "Божеволіти він нестримного програмування"),
            .init(id: "123", text: "Тім лід гомосексуаліст")
        ],
        state: .READY
    )
    @Published private(set) var players: [PlayerEntity] = []

    // MARK: - Public

    func showRound() {
        // TODO
    }
}
