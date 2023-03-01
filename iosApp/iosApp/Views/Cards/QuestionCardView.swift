//
//  QuestionCardView.swift
//  iosApp
//
//  Created by Artem Yelizarov on 21.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct QuestionCardView: View {
    let question: String

    // MARK: - Body

    var body: some View {
        ZStack {
            Color.mainBlack
            VStack {
                Text(question)
                    .multilineTextAlignment(.leading)
                    .lineLimit(11)
                    .foregroundColor(.white)
                    .font(.inputPrimary)
                Spacer()
            }
                .padding(.top, 20)
                .padding(.horizontal, .small)
        }
        .frame(width: 180, height: 240, alignment: .center)
        .cornerRadius(8)
        .shadow(color: .black.opacity(0.25), radius: 8, x: 0, y: 4)
    }
}

// MARK: - Previews

struct QuestionCard_Previews: PreviewProvider {
    static var previews: some View {
        QuestionCardView(
            question: """
                Під час візиту до лікаря мені дають \
                ___ та ___, щоб я почувався більш комфортно
                """
        )
    }
}
