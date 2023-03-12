//
//  Size.swift
//  iosApp
//
//  Created by Artem Yelizarov on 03.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

enum Size: CGFloat {
    /// Defaults to 1.0
    case smallest = 1
    /// Defaults to 8.0
    case small = 8
    /// Defaults to 16.0
    case medium = 16
    /// Defaults to 24.0
    case large = 24
    /// Defaults to 32.0
    case larger = 32
    /// Defaults to 48.0
    case extraLarge = 48
    /// Default Answer card aspect ratio
    case answerCardAspectRatio = 0.738
}

extension View {
    func padding(_ size: Size) -> some View {
        padding(size.rawValue)
    }

    func padding(_ edges: Edge.Set = .all, _ spacing: Size) -> some View {
        padding(edges, spacing.rawValue)
    }

    func frame(
        width: Size? = nil,
        height: Size? = nil,
        alignment: Alignment = .center
    ) -> some View {
        frame(
            width: width?.rawValue,
            height: height?.rawValue,
            alignment: alignment
        )
    }
}

extension VStack {
    init(alignment: HorizontalAlignment = .center, spacing: Size, @ViewBuilder content: () -> Content) {
        self.init(alignment: alignment, spacing: spacing.rawValue, content: content)
    }
}

extension HStack {
    init(alignment: VerticalAlignment = .center, spacing: Size, @ViewBuilder content: () -> Content) {
        self.init(alignment: alignment, spacing: spacing.rawValue, content: content)
    }
}
