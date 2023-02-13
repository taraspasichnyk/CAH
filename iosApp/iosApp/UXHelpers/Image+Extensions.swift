//
//  Image+Extensions.swift
//  iosApp
//
//  Created by Artem Yelizarov on 07.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

extension Image {
    static var bgTexture: Image {
        return .pattern
            .resizable(resizingMode: .tile)
    }
}
