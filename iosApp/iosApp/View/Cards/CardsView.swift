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
                PrimaryButton("Далі") {
                    // TODO
                }
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

    @Published var items: [CardItem] = []
//    [
//        .init(text: "Степан Гіга"),
//        .init(text: "Степан Гіга")
//    ]

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
