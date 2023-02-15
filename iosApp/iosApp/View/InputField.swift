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
    let onEditingChanged: (Bool) -> Void
    let onCommit: () -> Void

    init(_ prompt: String, text: Binding<String>, isFocused: FocusState<Bool>.Binding, onEditingChanged: @escaping (Bool) -> Void, onCommit: @escaping () -> Void = {}) {
        self.prompt = prompt
        self._text = text
        self.isFocused = isFocused
        self.onEditingChanged = onEditingChanged
        self.onCommit = onCommit
    }

    // MARK: - Body

    var body: some View {
        TextField(prompt, text: $text, onEditingChanged: onEditingChanged, onCommit: onCommit)
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
        InputField("Enter text", text: $text,  isFocused: $isFocused, onEditingChanged: { _ in })
            .previewLayout(.sizeThatFits)
    }
}
