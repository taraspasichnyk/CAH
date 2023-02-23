//
//  CurrentRoundView.swift
//  iosApp
//
//  Created by Artem Yelizarov on 21.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct CurrentRoundView: View {
    @State private var hasLoaded = false
    @State private var hasShiftedTitle = false
    let round: Int

    // MARK: - Body

    var body: some View {
        ZStack {
            ContainerView(header: .small) {
                VStack {
                    Spacer()
                    Text("Раунд \(round)")
                        .font(hasLoaded ? .titleSemiBold : .largeTitleSemiBold)
                    Spacer()
                    if hasLoaded {
                        if hasShiftedTitle {
                            QuestionCard(question: "Під час візиту до лікаря мені дають ___ та ___, щоб я почувався більш комфортно")
                        } else {
                            Spacer()
                        }
                        Spacer()
                        Spacer()
                        Text("Оберіть 2 відповіді:")
                            .font(.bodyTertiaryThin)
                        HandPickerView(
                            answers: [
                                "Гарний розмальований килим",
                                "Кинути важкі наркотики",
                                "Мій інструмент",
                                "Квашені огірочки",
                                "Светер з оленями",
                                "Заіржавілий жовтенький Богдан",
                                "Біла гарячка",
                            ]
                        )
//                    }
                }
            }
//            if hasLoaded {
                bottomButtonStack
//            }
        }
        .onAppear {
            DispatchQueue.main.asyncAfter(deadline: .now() + .seconds(1)) {
                withAnimation(.easeInOut(duration: 0.5)) {
                    hasLoaded = true
                }
            }
            DispatchQueue.main.asyncAfter(deadline: .now() + .milliseconds(1500)) {
                hasShiftedTitle = true
            }
        }
    }
}

// MARK: - Subviews

extension CurrentRoundView {
    private var bottomButtonStack: some View {
        return VStack {
            Spacer()
            HStack {
                Spacer()
                PrimaryButton("Обрати") {
                    print("SELECT CARD")
                }
                Spacer()
            }
            .padding(.bottom, 35)
            .overlay(
                HStack {
                    Spacer()
                    GridButton {
                        print("TOGGLE CARD VIEW")
                    }
                    .padding([.bottom, .trailing], 35)
                }
            )
        }
        .ignoresSafeArea()
    }
}

// MARK: - Hand

struct HandPickerView: View {
    @State private var selectedIndex: Int = 0

    var answers: [String]

    init(answers: [String]) {
        self.answers = answers
    }

    var body: some View {
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
                    guard selectedIndex < answers.count else { return }
                    withAnimation {
                        selectedIndex += 1
                    }
                }
                .scaleEffect(x: -1, y: 1, anchor: .center)
            }
            .padding(.horizontal, 46)
            ScrollView(.horizontal) {
                ZStack {
                    ForEach(Array(answers.enumerated()), id: \.offset) { (offset, answer) in
                        card(offset: offset, answer: answer)
                    }
                }
            }
        }
        .onAppear {
            selectedIndex = 0
        }
    }

    private func card(offset: Int, answer: String) -> some View {
        let isSelected = offset == selectedIndex
        var stackPosition = offset

        if offset < selectedIndex {
            stackPosition = -offset
        }

        return AnswerCard(answer: answer)
            .scaleEffect(
                x: isSelected ? 1 : 0.7,
                y: isSelected ? 1 : 0.7
            )
            .rotationEffect(
                .degrees(
                    rotation(
                        offset: offset,
                        selectedIndex: selectedIndex
                    )
                )
            )
            .shadow(
                color: .black.opacity(0.25),
                radius: 16,
                x: 0,
                y: 12
            )
            .padding(16)
            .zIndex(CGFloat(isSelected ? answers.count - 1 : offset))
            .offset(x: CGFloat(answers.count - stackPosition) * 58, y: 0)
    }

    private func rotation(offset: Int, selectedIndex: Int) -> CGFloat {
        if offset < selectedIndex {
            return 5
        } else if offset > selectedIndex {
            return -5
        } else {
            return 0
        }
    }
}

// MARK: - Previews

struct CurrentRoundView_Previews: PreviewProvider {
    static var previews: some View {
        CurrentRoundView(round: 1)
    }
}
