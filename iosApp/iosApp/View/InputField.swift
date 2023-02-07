//
//  InputField.swift
//  iosApp
//
//  Created by Artem Yelizarov on 07.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct InputField: View {

    @Binding private var text: String
    private let prompt: String

    init(_ prompt: String, text: Binding<String>) {
        self.prompt = prompt
        self._text = text
    }

    // MARK: - Body

    var body: some View {
        TextField(prompt, text: $text)
            .font(.inputPrimary)
            .padding(.vertical, .medium)
            .padding(.horizontal, .medium)
            .background(
                RoundedRectangle(cornerRadius: 4)
                    .strokeBorder(lineWidth: 1)
                    .background(.white)
                    .shadow(color: .black.opacity(0.15), radius: 4, y: 4)
            )
    }
}

// MARK: - Previews

struct InputField_Previews: PreviewProvider {
    @State private static var text = ""

    static var previews: some View {
        InputField("Enter text", text: $text)
            .previewLayout(.sizeThatFits)
    }
}
