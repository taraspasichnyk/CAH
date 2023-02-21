//
//  CardsView.swift
//  iosApp
//
//  Created by Taras Pasichnyk on 21.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct CardsView: View {
    var body: some View {
        ContainerView {
            VStack {
                Text("Ваші карти")
                    .font(.titleRegularSecondary)
                Spacer()
                HStack {
                    Spacer()
                    PrimaryButton("Далі") {
                        // TODO
                    }
                }
                .padding(.trailing, 20.0)
                .padding(.bottom, 52.0)
            }
            Spacer()
        }
        .ignoresSafeArea()
    }
}

struct CardsView_Previews: PreviewProvider {
    static var previews: some View {
        CardsView()
    }
}

struct CardItem: Identifiable {
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

    @Published var items: [CardItem] = [
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

    init(gvm: GameViewModel) {
        AnyFlow<GameContractGameState>(source: gvm.state).collect { gameState in
            // TODO
//            self.items = gameState.items
        } onCompletion: { error in
            // TODO
            print(error)
        }
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

struct GridItemView: View {
    let size: Double
    let item: CardItem

    var body: some View {
        ZStack(alignment: .topTrailing) {
            Text(item.text)
                .frame(width: size, height: size)
        }
    }
}
