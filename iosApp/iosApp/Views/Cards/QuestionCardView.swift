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
        VStack {
            Text(question)
                .frame(maxWidth: .infinity)
                .multilineTextAlignment(.leading)
                .padding(.top, .medium)
                .padding(.horizontal, .small)
                .foregroundColor(.white)
            Spacer()
        }
        .background(Color.mainBlack)
        .aspectRatio(124 / 168, contentMode: .fit)
        .cornerRadius(8.0)
//        .shadow(radius: 8.0, y: 4.0)
    }
}

// MARK: - Previews

struct QuestionCard_Previews: PreviewProvider {
    static var previews: some View {
        VStack(spacing: 8.0) {
            QuestionCardView(
                question: """
                    Під час візиту до лікаря мені дають \
                    ___ та ___, щоб я почувався більш комфортно
                    """
            )
            .frame(width: 180)
            .font(.inputPrimary)
            QuestionCardView(
                question: """
                    Під час візиту до лікаря мені дають \
                    ___ та ___, щоб я почувався більш комфортно
                    """
            )
            .frame(width: 124)
            .font(.cardSmall)
        }
    }
}
