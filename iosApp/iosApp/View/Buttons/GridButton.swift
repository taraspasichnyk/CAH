//
//  GridButton.swift
//  iosApp
//
//  Created by Artem Yelizarov on 21.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct GridButton: View {
    let action: () -> Void

    // MARK: - Body

    var body: some View {
        Button(action: action) {
            Image.grid
                .square(.large)
                .padding(.medium)
                .background(.white)
        }
            .clipShape(Circle())
            .shadow(color: .black.opacity(0.25), radius: 16, x: 0, y: 12)
    }
}

// MARK: - Previews

struct GridButton_Previews: PreviewProvider {
    static var previews: some View {
        GridButton {}
    }
}
