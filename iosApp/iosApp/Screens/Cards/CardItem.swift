//
//  CardItem.swift
//  iosApp
//
//  Created by Taras Pasichnyk on 27.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation

struct CardItem: Identifiable, Hashable {
    let id = UUID()
    let text: String
}
