//
//  Array+Extensions.swift
//  iosApp
//
//  Created by Artem Yelizarov on 15.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation

extension Array {
    mutating func removeAll(after index: Int) {
        let dropIndex = count - index - 1
        guard indices.contains(dropIndex) else { return }

        if dropIndex > 0 {
            removeLast(dropIndex)
        } else {
            removeLast()
        }
    }
}
