//
//  LoadingModifier.swift
//  iosApp
//
//  Created by Artem Yelizarov on 21.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

extension View {
    /// Adds generic loading indicator
    func loading(_ isLoading: Bool) -> some View {
        modifier(
            LoadingModifier(isLoading: isLoading)
        )
    }
}

// MARK: - Modifier

struct LoadingModifier: ViewModifier {
    let isLoading: Bool

    func body(content: Content) -> some View {
        if isLoading {
            content.overlay(
                ZStack {
                    Color.white
                        .opacity(0.9)
                        .blur(radius: 0.5)
                    ProgressView()
                        .tint(.mainBlack)
                }
                    .ignoresSafeArea(.all)
            )
        } else {
            content
        }
    }
}
