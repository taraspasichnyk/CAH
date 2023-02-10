//
//  NavigationState.swift
//  iosApp
//
//  Created by Artem Yelizarov on 09.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation

/// Local state representing navigation that starts from `Main Menu`
enum NavigationState {
    case enterCode
    case enterName
    case lobby
    case game
}
