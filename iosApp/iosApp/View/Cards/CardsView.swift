//
//  CardsView.swift
//  iosApp
//
//  Created by Taras Pasichnyk on 21.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct CardsView: View {
    @EnvironmentObject var dataModel: CardItemsDataModel

    @State var animate: Bool = false

    let columns = [
        GridItem(spacing: 8.0),
        GridItem(spacing: 8.0),
        GridItem(spacing: 8.0)
    ]

    let spacing = 8.0

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
                            VStack {
                                Text(item.text)
                                    .frame(maxWidth: .infinity)
                                    .multilineTextAlignment(.center)
                                    .padding(.top, 16.0)
                                    .padding(.horizontal, 8.0)
                                    .font(.cardSmall)
                                Spacer()
                            }
                            .scaleEffect(animate ? 1.3 : 1.0)
                            .aspectRatio(124 / 168, contentMode: .fill)
                            .background(
                                LinearGradient(
                                    colors: [
                                        Color(red: 0.94, green: 0.94, blue: 0.94),
                                        Color.white,
                                        Color(red: 0.92, green: 0.92, blue: 0.92),
                                    ],
                                    startPoint: .topLeading,
                                    endPoint: .bottomTrailing
                                )
                            )
                            .compositingGroup()
                            .cornerRadius(8.0)
                            .shadow(radius: 8.0, y: 4.0)
                        }
                    }
                    .padding([.leading, .trailing], 20.0)
                }
                VStack {
                    Spacer()
                    HStack {
                        Spacer()
                        PrimaryButton("Далі") {
                            withAnimation(.easeOut) {
                                animate.toggle()
                            }
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

struct GridItemView: View {
    let width: Double
    let height: Double
    let item: CardItem

    var body: some View {
        ZStack(alignment: .topTrailing) {
            AnswerCard(answer: item.text)
                .frame(width: width, height: height)
        }
    }
}
