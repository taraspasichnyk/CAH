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
            self?.items = player.cards.compactMap({ CardItem(text: $0.answer) })
        } onCompletion: { _ in
        }
    }
}

// MARK: - Mock

class MockGameModel: GameModelProtocol {

    // MARK: - Properties

    @Published private(set) var items: [CardItem] = [
        .init(text: "Степан Гіга"),
        .init(text: "Знімати персики з дерева біля ЖЕКу"),
        .init(text: "Місити палкою кропиву"),
        .init(text: "Неймовірний покемон Сквіртл"),
        .init(text: "Картонний пакет Кагору"),
        .init(text: "Футбольний клуб \"Карпати\""),
        .init(text: "Майнити біткойни на Atari"),
        .init(text: "Стрілецька Дивізія \"СС Галичина\""),
        .init(text: "Божеволіти він нестримного програмування"),
        .init(text: "Тім лід гомосексуаліст")
    ]
    @Published private(set) var round: GameRound? = nil
    @Published private(set) var player: Player? = nil

    // MARK: - Public

    func showRound() {
        // TODO
    }
}
