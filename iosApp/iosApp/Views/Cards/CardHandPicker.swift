//
//  CardHandPicker.swift
//  iosApp
//
//  Created by Artem Yelizarov on 26.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct CardHandPicker: View {
    @Binding var selectedIndex: Int

    var answers: [String]

    // MARK: - Body

    var body: some View {
        ZStack {
            ScrollView(.horizontal) {
                ZStack {
                    ForEach(Array(answers.enumerated()), id: \.offset) { (offset, answer) in
                        AnswerCard(answer: answer)
                            .font(.inputPrimary)
                            .aspectRatio(124 / 168, contentMode: .fit)
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
                            selectedIndex -= 1
                        }
                    }
                    Spacer()
                    BackButton {
                        guard selectedIndex < answers.count - 1 else { return }
                        withAnimation {
                            selectedIndex += 1
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
    @State private static var selectedIndex = 0
    private static let answers = [
        "Гарний розмальований килим",
        "Кинути важкі наркотики",
        "Мій інструмент",
        "Квашені огірочки",
        "Светер з оленями",
        "Заіржавілий жовтенький Богдан",
        "Біла гарячка",
    ]

    static var previews: some View {
        CardHandPicker(
            selectedIndex: $selectedIndex,
            answers: answers
        )
        .frame(height: 268)
        .previewLayout(.sizeThatFits)
    }
}
