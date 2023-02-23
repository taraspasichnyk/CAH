//
//  SizeModifier.swift
//  iosApp
//
//  Created by Taras Pasichnyk on 23.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct SquareSizeModifier: ViewModifier {
    let size: Size

    func body(content: Content) -> some View {
        content.frame(width: size, height: size)
    }
}

extension View {
    func square(_ size: Size) -> some View {
        modifier(SquareSizeModifier(size: size))
    }
}
