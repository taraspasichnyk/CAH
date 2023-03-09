//
//  RateView.swift
//  iosApp
//
//  Created by Taras Pasichnyk on 01.03.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct RateView: View {

    enum Value: Int, CaseIterable, Identifiable {
        case one = 1
        case two = 2
        case three = 3
        case four = 4

        var id: Int { rawValue }

        var image: Image {
            switch self {
            case .one: return .one
            case .two: return .two
            case .three: return .three
            case .four: return .four
            }
        }
    }

    private var selectedValue: Int
    private let action: (Int) -> Void

    init(_ selectedValue: Int, action: @escaping (Int) -> Void) {
        self.selectedValue = selectedValue
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
                ForEach(Value.allCases) { value in
                    IconButton(value.image.resizable()) { action(value.rawValue) }
                        .opacity(value.rawValue != selectedValue ? 1 : 0.33)
                        .scaleEffect(value.rawValue != selectedValue ? 1 : 0.4)
                        .aspectRatio(1.0, contentMode: .fit)
                }
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

// MARK: - Previews

struct RateView_Previews: PreviewProvider {

    static var previews: some View {
        RateView(1) { value in
            print(value)
        }
        .padding([.leading, .trailing], 50)
    }
}
