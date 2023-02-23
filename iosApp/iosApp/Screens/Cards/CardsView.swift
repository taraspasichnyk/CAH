//
//  CardsView.swift
//  iosApp
//
//  Created by Taras Pasichnyk on 21.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct CardsView: View {

    // MARK: - Properties

    @State private var items: [CardItem] = [
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

    private let vm: GameViewModel

    private let columns = [
        GridItem(spacing: 8.0),
        GridItem(spacing: 8.0),
        GridItem(spacing: 8.0)
    ]

    private let spacing = 8.0

    // MARK: - Lifecycle

    init(vm: GameViewModel) {
        self.vm = vm
    }

    var body: some View {
        ContainerView(header: .small) {
            ZStack {
                ScrollView(.vertical) {
                    Text("Ваші карти")
                        .font(.titleRegularSecondary)
                    LazyVGrid(
                        columns: columns,
                        alignment: .center,
                        spacing: spacing
                    ) {
                        ForEach(items, id: \.self) { item in
                            AnswerCard(answer: item.text)
                                .font(.cardSmall)
                        }
                    }
                    .padding([.leading, .trailing], 20.0)
                }
                VStack {
                    Spacer()
                    HStack {
                        Spacer()
                        PrimaryButton("Далі") {
                            vm.showRound()
                        }
                    }
                    .padding(.trailing, 20.0)
                }
            }
            Spacer()
        }
        .ignoresSafeArea()
        .onAppear {
            subscribeToState()
        }
    }

    // MARK: - Private

    private func subscribeToState() {
        AnyFlow<GameContractState>(source: vm.state).collect { state in
            guard let state else { return }
            guard let room = state.room else { return }
            guard let player = vm.getSelfFromState(gameState: state) else { return }
            items = player.cards.compactMap({ CardItem(text: $0.answer) })
        } onCompletion: { _ in
        }
    }
}

// MARK: - Previews

struct CardsView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            CardsView(vm: .init(roomId: "484172", playerId: "-NOxn4NgUTxDzyRpcA0J"))
//            CardsView(vm: .init(roomId: "484172", playerId: "-NOxn4NgUTxDzyRpcA0J"))
//                .previewDevice(.init(rawValue: "iPhone SE (3rd generation)"))
        }
    }
}

struct CardItem: Identifiable, Hashable {
    let id = UUID()
    let text: String
}

