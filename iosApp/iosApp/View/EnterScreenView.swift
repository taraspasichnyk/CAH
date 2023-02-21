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

    @EnvironmentObject private var loadingState: LoadingState
    @EnvironmentObject private var alert: AlertState
    @FocusState private var isFocused: Bool

    @State private var inputText = ""
    @State private var isButtonEnabled = false
    @State private var buttonTitle = ""

    let stage: EnterScreenStage

    private var vm: LobbyViewModel {
        switch stage {
        case .roomCode(let vm), .playerName(let vm):
            return vm
        }
    }
    
    // MARK: - Body

    var body: some View {
        let nameBinding = Binding {
            inputText
        } set: {
            inputText = $0
            validateInput()
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
                    PrimaryButton(buttonTitle) {
                        vm.onNextClicked()
                    }
                    .disabled(!isButtonEnabled)
                    .buttonLoading(loadingState.isLoading)
                }
                .padding(.leading, 40)
                .padding(.trailing, 36)
            }
        }
        .onAppear {
            isFocused = true
            subscribeToState()
        }
    }
}

// MARK: - Private

extension EnterScreenView {
    private func subscribeToState() {
        AnyFlow<LobbyContractState>(source: vm.state).collect { state in
            guard let state else { return }
            isButtonEnabled = state.isNextButtonEnabled
            buttonTitle = state.buttonText
            loadingState.isLoading = state.isLoading
        } onCompletion: { _ in
        }
    }

    private func validateInput() {
        switch stage {
        case .playerName(let lobbyVm):
            lobbyVm.validateName(name: inputText)
        case .roomCode(let lobbyVm):
            lobbyVm.validateCode(code: inputText)
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
