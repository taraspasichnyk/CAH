//
//  EnterScreenView.swift
//  iosApp
//
//  Created by Artem Yelizarov on 03.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct EnterScreenView: View {

    @State private var name = ""
    @EnvironmentObject private var alert: AlertState
    @Binding var navState: [NavigationState]

    let stage: EnterScreenStage

    // MARK: - Body

    var body: some View {
        ContainerView {
            Spacer(minLength: 90)
            Text(stage.prompt)
                .font(.bodyPrimary)
            InputField(stage.placeholder, text: $name)
                .frame(width: 286)
            Spacer(minLength: 112)
            HStack {
                BackButton {
                    _ = navState.popLast()
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
        }
        .navigationBarBackButtonHidden(true)
    }
}

// MARK: - Previews

struct EnterNameView_Previews: PreviewProvider {
    static var previews: some View {
        EnterScreenView(navState: .constant([]), stage: .playerName)
    }
}
