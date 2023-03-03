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
    var isFocused: FocusState<Bool>.Binding

    init(_ prompt: String, text: Binding<String>, isFocused: FocusState<Bool>.Binding) {
        self.prompt = prompt
        self._text = text
        self.isFocused = isFocused
    }

    // MARK: - Body

    var body: some View {
        TextField(prompt, text: $text)
            .focused(isFocused)
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
    @FocusState private static var isFocused: Bool

    static var previews: some View {
        InputField("Enter text", text: $text,  isFocused: $isFocused)
            .previewLayout(.sizeThatFits)
    }
}
