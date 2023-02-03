//
//  PrimaryButton.swift
//  iosApp
//
//  Created by Artem Yelizarov on 03.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct PrimaryButton: View {
    let title: any StringProtocol
    let action: () -> Void

    init(_ title: String, action: @escaping () -> Void) {
        self.title = title
        self.action = action
    }

    // MARK: - Body

    var body: some View {
        Button(action: action) {
            Text(title)
                .padding(.vertical, .medium)
                .padding(.horizontal, .extraLarge)
                .background(Color.mainBlack)
                .cornerRadius(2)
                .foregroundColor(.white)
                .buttonStyle(Style())
        }
    }
}

// MARK: - Style

extension PrimaryButton {
    struct Style: ButtonStyle {
        func makeBody(configuration: Configuration) -> some View {
            configuration.label
                .opacity(configuration.isPressed ? 0.33 : 1)
        }
    }
}

// MARK: - Preivews

struct PrimaryButton_Previews: PreviewProvider {
    static var previews: some View {
        PrimaryButton("Button", action: {})
    }
}
