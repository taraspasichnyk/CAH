//
//  Size.swift
//  iosApp
//
//  Created by Artem Yelizarov on 03.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

enum Size: CGFloat {
    case smallest = 1
    case small = 8
    case medium = 16
    case large = 24
    case extraLarge = 48
}

struct SquareSize: ViewModifier {
    let size: Size

    init(_ size: Size) {
        self.size = size
    }

    func body(content: Content) -> some View {
        content.frame(width: size, height: size)
    }
}

extension View {
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

    func square(_ size: Size) -> some View {
        modifier(SquareSize(size))
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
