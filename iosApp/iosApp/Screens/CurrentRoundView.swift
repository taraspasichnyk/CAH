//
//  CurrentRoundView.swift
//  iosApp
//
//  Created by Artem Yelizarov on 21.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct CurrentRoundView: View {
    @State private var hasLoaded = false
    @State private var hasShiftedTitle = false
    @State private var selectedIndex = 0
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
                        CardHandPicker(
                            selectedIndex: $selectedIndex,
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
                        .padding(.bottom, .large)
                    }
                }
            }
            if hasLoaded {
                bottomButtonStack
            }
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
                    /*
                    vm.saveAnswers(answerCardIds: [
                        // TODO: use cards
                    ])
                     */
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

// MARK: - Previews

struct CurrentRoundView_Previews: PreviewProvider {
    static var previews: some View {
        CurrentRoundView(round: 1)
    }
}
