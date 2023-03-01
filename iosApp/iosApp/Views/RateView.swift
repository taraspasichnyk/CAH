//
//  RateView.swift
//  iosApp
//
//  Created by Taras Pasichnyk on 01.03.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct RateView: View {

    private let action: (Int) -> Void

    init(action: @escaping (Int) -> Void) {
        self.action = action
    }

    var body: some View {
        VStack(spacing: 16) {
            Text("Оцініть відповідь")
                .padding(.small)
                .cornerRadius(2)
                .background(Color.mainBlack)
                .foregroundColor(.white)
                .font(.titleRegularPrimary)
            HStack(spacing: .large) {
                IconButton(.one) { action(1) }
                IconButton(.two) { action(2) }
                IconButton(.three) { action(3) }
                IconButton(.four) { action(4) }
            }
        }
        .padding(16.0)
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

struct RateView_Previews: PreviewProvider {
    static var previews: some View {
        RateView { value in
            print(value)
        }
        .padding([.leading, .trailing], 50)
    }
}
