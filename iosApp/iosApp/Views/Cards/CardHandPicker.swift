//
//  CardHandPicker.swift
//  iosApp
//
//  Created by Artem Yelizarov on 26.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct CardHandPicker: View {
    @Binding var selectedCard: AnswerCardEntity
    @Binding var answers: [AnswerCardEntity]

    private var selectedIndex: Int {
        answers.firstIndex(of: selectedCard) ?? 0
    }

    // MARK: - Body

    var body: some View {
        ZStack {
            ScrollView(.horizontal) {
                ZStack {
                    ForEach(Array(answers.enumerated()), id: \.offset) { (offset, answerItem) in
                        AnswerCardView(answer: answerItem.text)
                            .font(.inputPrimary)
                            .frame(width: 180, height: 240)
                            .scaleEffect(getScale(offset), anchor: .bottom)
                            .rotationEffect(.degrees(getRotation(offset)))
                            .offset(x: CGFloat(offset) * 90)
                            .zIndex(getZIndex(offset))
                    }
                }
            }
            .content.offset(x: -CGFloat(90 * selectedIndex))
            VStack {
                HStack {
                    BackButton {
                        guard selectedIndex > 0 else { return }
                        withAnimation {
                            let index = selectedIndex - 1
                            selectedCard = answers[index]
                        }
                    }
                    Spacer()
                    BackButton {
                        guard selectedIndex < answers.count - 1 else { return }
                        withAnimation {
                            let index = selectedIndex + 1
                            selectedCard = answers[index]
                        }
                    }
                    .scaleEffect(x: -1, y: 1, anchor: .center)
                }
                .padding(.horizontal, 26) // horizontal padding 46 in Figma
                Spacer()
            }
        }
    }
}

// MARK: - Private

extension CardHandPicker {
    private func getScale(_ offset: Int) -> CGFloat {
        return (offset == selectedIndex) ? 1 : 0.7
    }

    private func getZIndex(_ offset: Int) -> CGFloat {
        return CGFloat(offset <= selectedIndex ? offset : -offset)
    }

    private func getRotation(_ offset: Int) -> CGFloat {
        if offset < selectedIndex {
            return -5
        } else if offset == selectedIndex {
            return 0
        } else {
            return 5
        }
    }
}

// MARK: - Previews

struct CardHandPicker_Previews: PreviewProvider {
    @State private static var selectedCard = AnswerCardEntity(
        id: UUID().uuidString,
        text: "Гарний розмальований килим"
    )
    private static let answers: [AnswerCardEntity] = [selectedCard] + [
        "Кинути важкі наркотики",
        "Мій інструмент",
        "Квашені огірочки",
        "Светер з оленями",
        "Заіржавілий жовтенький Богдан",
        "Біла гарячка",
    ]
        .map {
            AnswerCardEntity(id: UUID().uuidString, text: $0)
        }

    static var previews: some View {
        CardHandPicker(
            selectedCard: $selectedCard,
            answers: .constant(answers)
        )
        .frame(height: 268)
        .previewLayout(.sizeThatFits)
    }
}
