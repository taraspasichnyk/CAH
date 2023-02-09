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

    @Binding var navState: [NavigationState]
    @EnvironmentObject private var alert: AlertState
    @FocusState private var isFocused: Bool

    @State private var name = ""

    let stage: EnterScreenStage

    // MARK: - Body

    var body: some View {
        ZStack {
            VStack {
                Spacer()
                Image.bgTexture
                    .frame(height: .extraLarge)
            }
            .ignoresSafeArea(.all)
            VStack {
                HeaderView()
                    .frame(height: 210)
                    .fixedSize(horizontal: false, vertical: true)
                    .ignoresSafeArea(.all)
                Spacer()
                InputField(stage.placeholder, text: $name, isFocused: $isFocused)
                    .frame(width: 286)
                Spacer()
                Spacer()
                VStack {
                    HStack {
                        BackButton {
                            // TODO: Replace with call to viewmodel
                            _ = navState.popLast()
                        }
                        Spacer()
                        PrimaryButton("Далі") {
                            // TODO: Replace with call to viewmodel
                            alert.isPresentingNoFeature = true
                        }
                        .disabled(name.isEmpty)
                    }
                    .padding(.leading, 40)
                    .padding(.trailing, 36)
                    .padding(.bottom, Size.extraLarge.rawValue)
                }
            }

        }
        .navigationBarBackButtonHidden(true)
        .onAppear {
            isFocused = true
        }
    }
}

// MARK: - Previews

struct EnterNameView_Previews: PreviewProvider {
    static var previews: some View {
        EnterScreenView(navState: .constant([]), stage: .playerName)
    }
}
