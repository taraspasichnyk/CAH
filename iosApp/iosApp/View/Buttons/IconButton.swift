//
//  IconButton.swift
//  iosApp
//
//  Created by Artem Yelizarov on 09.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct IconButton: View {
    let icon: Image
    let action: () -> Void

    init(_ icon: Image, action: @escaping () -> Void) {
        self.icon = icon
        self.action = action
    }

    // MARK: - Body

    var body: some View {
        Button(action: action) {
            icon
        }
    }
}

// MARK: - Previews

struct IconButton_Previews: PreviewProvider {
    static var previews: some View {
        IconButton.init(.host, action: {})
    }
}
