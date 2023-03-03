//
//  HeaderSize.swift
//  iosApp
//
//  Created by Artem Yelizarov on 21.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation

enum HeaderSize {
    case small
    case medium

    var height: CGFloat {
        switch self {
        case .small: return 100
        case .medium: return 180
        }
    }

    var logoWidth: CGFloat {
        switch self {
        case .small: return 180
        case .medium: return 238
        }
    }

    var headerBackgroundBottomPadding: CGFloat {
        switch self {
        case .small: return 18
        case .medium: return 28
        }
    }
}
