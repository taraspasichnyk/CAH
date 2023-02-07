//
//  EnterNameView.swift
//  iosApp
//
//  Created by Artem Yelizarov on 03.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct EnterNameView: View {

    @State private var name = ""
    @EnvironmentObject private var alert: AlertState
    let vm: GameViewModel

    // MARK: - Body

    var body: some View {
        VStack(alignment: .center) {
            HeaderView()
            Spacer(minLength: 90)
            Text("Введіть імʼя")
                .font(.bodyPrimary)
            InputField("Ваше імʼя", text: $name)
                .frame(width: 286)
            Spacer(minLength: 112)
            HStack {
                BackButton {
                    alert.isPresentingNoFeature = true
                }
                Spacer()
                PrimaryButton("Далі") {
                    alert.isPresentingNoFeature = true
                }
                .disabled(name.isEmpty)
            }
            .padding(.leading, 40)
            .padding(.trailing, 36)
            .padding(.bottom, 42)
            Image.bgTexture
                .frame(height: .extraLarge)
        }
        .ignoresSafeArea()
    }
}

// MARK: - Previews

struct EnterNameView_Previews: PreviewProvider {
    static var previews: some View {
        EnterNameView(vm: .init())
    }
}
