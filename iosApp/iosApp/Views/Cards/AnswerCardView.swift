//
//  AnswerCardView.swift
//  iosApp
//
//  Created by Artem Yelizarov on 19.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct AnswerCardView: View {

    let answer: String

    // MARK: - Body

    var body: some View {
        VStack {
            Text(answer)
                .frame(maxWidth: .infinity)
                .multilineTextAlignment(.center)
                .padding(.top, .medium)
                .padding(.horizontal, .small)
                .foregroundColor(.mainBlack)
            Spacer()
        }
        .aspectRatio(124 / 168, contentMode: .fit)
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

// MARK: - Previews

struct AnswerCard_Previews: PreviewProvider {
    static var previews: some View {
        VStack(spacing: 8.0) {
            AnswerCardView(answer: "Гарний розмальований килим")
                .frame(width: 180)
                .font(.inputPrimary)
            AnswerCardView(answer: "Гарний розмальований килим")
                .frame(width: 124)
                .font(.cardSmall)
        }
    }
}
