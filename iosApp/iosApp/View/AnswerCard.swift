//
//  AnswerCard.swift
//  iosApp
//
//  Created by Artem Yelizarov on 19.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct AnswerCard: View {
    let answer: String

    // MARK: - Body

    var body: some View {
        LinearGradient(
            colors: [
                Color(red: 0.94, green: 0.94, blue: 0.94),
                Color.white,
                Color(red: 0.92, green: 0.92, blue: 0.92),
            ],
            startPoint: .topLeading,
            endPoint: .bottomTrailing
        )
        .frame(width: 180, height: 240, alignment: .center)
        .cornerRadius(8)
        .shadow(color: .black.opacity(0.25), radius: 16, x: 0, y: 12)
        .overlay(
            VStack {
                Text(answer)
                    .multilineTextAlignment(.center)
                    .padding(.top, 20)
                    .padding(.horizontal, .medium)
                Spacer()
            }
                .font(.inputPrimary)
        )
    }
}

// MARK: - Previews

struct AnswerCard_Previews: PreviewProvider {
    static var previews: some View {
        AnswerCard(answer: "Гарний розмальований килим")
    }
}
