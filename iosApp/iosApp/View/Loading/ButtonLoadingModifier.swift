//
//  ButtonLoadingModifier.swift
//  
//
//  Created by Artem Yelizarov on 21.02.2023.
//

import SwiftUI

extension View {
    /// Loading indicator that should be used with buttons
    func buttonLoading(_ isLoading: Bool) -> some View {
        modifier(
            ButtonLoadingModifier(isLoading: isLoading)
        )
    }
}

// MARK: - Modifier

struct ButtonLoadingModifier: ViewModifier {
    let isLoading: Bool

    func body(content: Content) -> some View {
        if isLoading {
            content.overlay(
                ZStack {
                    Color.mainBlack
                    ProgressView()
                        .tint(.white)
                }
                    .cornerRadius(2)
            )
        } else {
            content
        }
    }
}
