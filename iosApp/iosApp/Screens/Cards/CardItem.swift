//
//  CardItem.swift
//  iosApp
//
//  Created by Taras Pasichnyk on 27.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation

struct CardItem: Identifiable, Hashable {
    let id: String
    let text: String
}

extension CardItem {
    static let placeholder: Self = .init(id: "", text: "")
}
