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
    var items: [CardItem] { get }
    var round: GameRound? { get }
    var player: Player? { get }

    func showRound()
}

class GameModel: GameModelProtocol {

    // MARK: - Properties

    private let vm: GameViewModel

    @Published private(set) var items: [CardItem] = []
    @Published private(set) var round: GameRound? = nil
    @Published private(set) var player: Player? = nil

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
            self?.items = player.cards.compactMap({ CardItem(id: $0.id, text: $0.answer) })
        } onCompletion: { _ in
        }
    }
}

// MARK: - Mock

class MockGameModel: GameModelProtocol {

    // MARK: - Properties

    @Published private(set) var items: [CardItem] = [
        "Степан Гіга",
        "Знімати персики з дерева біля ЖЕКу",
        "Місити палкою кропиву",
        "Неймовірний покемон Сквіртл",
        "Картонний пакет Кагору",
        "Футбольний клуб \"Карпати\"",
        "Майнити біткойни на Atari",
        "Стрілецька Дивізія \"СС Галичина\"",
        "Божеволіти він нестримного програмування",
        "Тім лід гомосексуаліст",
    ]
        .map {
            CardItem(id: UUID().uuidString, text: $0)
        }
    @Published private(set) var round: GameRound? = nil
    @Published private(set) var player: Player? = nil

    // MARK: - Public

    func showRound() {
        // TODO
    }
}
