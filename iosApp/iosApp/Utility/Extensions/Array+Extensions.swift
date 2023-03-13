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

    mutating func removeElement(_ element: Element) where Element: Equatable {
        guard let elementIndex = self.firstIndex(where: { $0 == element }) else { return }
        self.remove(at: elementIndex)
    }
}
