//
//  CardsView.swift
//  iosApp
//
//  Created by Taras Pasichnyk on 21.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct CardsView: View {

    // MARK: - Properties

    @EnvironmentObject var dataModel: CardItemsDataModel

//    private let vm: GameViewModel

    private let columns = [
        GridItem(spacing: 8.0),
        GridItem(spacing: 8.0),
        GridItem(spacing: 8.0)
    ]

    private let spacing = 8.0

    // MARK: - Lifecycle

//    init(vm: GameViewModel) {
//        self.vm = vm
//    }

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
                        ForEach(dataModel.items, id: \.self) { item in
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
                            // TODO
                        }
                    }
                    .padding(.trailing, 20.0)
                }
            }
            Spacer()
        }
        .ignoresSafeArea()
        .onAppear()
    }
}

struct CardsView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            CardsView()
                .environmentObject(CardItemsDataModel())
            CardsView()
                .environmentObject(CardItemsDataModel())
                .previewDevice(.init(rawValue: "iPhone SE (3rd generation)"))
//            CardsView(vm: .init(roomId: "", playerId: ""))
//                .environmentObject(CardItemsDataModel())
//            CardsView(vm: .init(roomId: "", playerId: ""))
//                .environmentObject(CardItemsDataModel())
//                .previewDevice(.init(rawValue: "iPhone SE (3rd generation)"))
        }
    }
}

struct CardItem: Identifiable, Hashable {
    let id = UUID()
    let text: String
}

extension CardItem: Equatable {
    static func ==(lhs: CardItem, rhs: CardItem) -> Bool {
        return lhs.id == rhs.id && lhs.id == rhs.id
    }
}

import shared

class CardItemsDataModel: ObservableObject {

    @Published var items: [CardItem] = []

    init() {
        items = [
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
    }

    init(gvm: GameViewModel) {
//        AnyFlow<GameContractGameState>(source: gvm.state).collect { gameState in
//            // TODO
////            self.items = gameState.items
//        } onCompletion: { error in
//            // TODO
//            print(error)
//        }
    }

    /// Adds an item to the data collection.
    func addItem(_ item: CardItem) {
        items.insert(item, at: 0)
    }

    /// Removes an item from the data collection.
    func removeItem(_ item: CardItem) {
        if let index = items.firstIndex(of: item) {
            items.remove(at: index)
        }
    }
}
