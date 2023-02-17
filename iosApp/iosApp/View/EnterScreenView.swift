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

    @EnvironmentObject private var alert: AlertState
    @FocusState private var isFocused: Bool

    @State private var name = ""
    @State private var isButtonEnabled = false

    let stage: EnterScreenStage

    var vm: LobbyViewModel {
        switch stage {
        case .roomCode(let vm), .playerName(let vm):
            return vm
        }
    }
    
    // MARK: - Body

    var body: some View {
        let nameBinding = Binding<String> {
            self.name
        } set: {
            self.name = $0

            switch stage {
            case .playerName(let lobbyVm):
                lobbyVm.validateName(name: name)
            case .roomCode(let lobbyVm):
                lobbyVm.validateCode(code: name)
            }
        }

        ContainerView {
            Spacer()
            InputField(stage.placeholder, text: nameBinding, isFocused: $isFocused)
            .textContentType(stage.contentType)
            .frame(width: 286)
            Spacer()
            VStack {
                HStack {
                    BackButton {
                        vm.onBackPressed()
                    }
                    Spacer()
                    PrimaryButton("Далі") {
                        vm.onNextClicked()
                    }
                    .disabled(name.isEmpty)
                }
                .padding(.leading, 40)
                .padding(.trailing, 36)
                .padding(.bottom, .extraLarge)
            }
        }
        .onAppear {
            isFocused = true
            subscribeToState()
        }
    }

    private func subscribeToState() {
        AnyFlow<LobbyContractState>(source: vm.state).collect { state in
            guard let state else { return }
            self.isButtonEnabled = state.isNextButtonEnabled
        } onCompletion: { _ in
        }
    }
}

// MARK: - Previews

struct EnterNameView_Previews: PreviewProvider {
    static var previews: some View {
        EnterScreenView(
            stage: .playerName(.init())
        )
    }
}
