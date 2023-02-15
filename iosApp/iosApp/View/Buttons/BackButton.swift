//
//  BackButton.swift
//  iosApp
//
//  Created by Artem Yelizarov on 03.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct BackButton: View {
    let action: () -> Void

    // MARK: - Body

    var body: some View {
        Button(action: action) {
            Image.back
                .square(.extraLarge)
        }
    }
}

// MARK: - Previews

struct BackButton_Previews: PreviewProvider {
    static var previews: some View {
        BackButton {}
    }
}
