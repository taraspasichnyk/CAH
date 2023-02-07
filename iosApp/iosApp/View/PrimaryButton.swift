//
//  PrimaryButton.swift
//  iosApp
//
//  Created by Artem Yelizarov on 03.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct PrimaryButton: View {
    @Environment(\.isEnabled) private var isEnabled

    private let title: any StringProtocol
    private let action: () -> Void

    init(_ title: String, action: @escaping () -> Void) {
        self.title = title
        self.action = action
    }

    // MARK: - Body

    var body: some View {
        Button(action: action) {
            Text(title)
                .font(.button)
                .padding(.vertical, .medium)
                .padding(.horizontal, .extraLarge)
                .background(
                    Color.mainBlack
                        .opacity(isEnabled ? 1 : 0.33)
                )
                .cornerRadius(2)
                .foregroundColor(.white)
        }
    }
}

// MARK: - Preivews

struct PrimaryButton_Previews: PreviewProvider {
    static var previews: some View {
        VStack(spacing: Size.medium) {
            PrimaryButton("Enabled", action: {})
            PrimaryButton("Disabled", action: {})
                .disabled(true)
        }
    }
}
