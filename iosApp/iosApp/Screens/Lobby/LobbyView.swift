//
//  LobbyView.swift
//  iosApp
//
//  Created by Artem Yelizarov on 09.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LobbyView: View {
    @EnvironmentObject private var alert: AlertState
    @EnvironmentObject private var loadingState: LoadingState

    @State private var users: [Player] = []
    @State private var roomCode: String = "000000"
    @State private var isButtonEnabled = false
    @State private var buttonTitle = "Готовий"

    private let vm: LobbyViewModel

    init(vm: LobbyViewModel) {
        self.vm = vm
    }

    // MARK: - Body

    var body: some View {
        ContainerView {
            VStack {
                HStack {
                    VStack(alignment: .leading, spacing: Size.medium.rawValue) {
                        Text("Код для приєднання")
                            .font(.lightBodySecondary)
                        Text(roomCode)
                            .font(.titleBoldSecondary)
                    }
                    Spacer()
                    IconButton(.copy) {
                        vm.onCodeCopyClicked()
                    }
                    .square(.larger)
                    Spacer()
                    ShareLink(item: roomCode) {
                        Image.share
                    }
                    .square(.larger)
                }
                .padding(.medium)
                .overlay(
                    RoundedRectangle(cornerRadius: 4)
                        .stroke(Color.mainBlack, lineWidth: 1)
                )
                .padding(.horizontal, .extraLarge)

                ScrollView(.vertical) {
                    LazyVStack {
                        ForEach(Array(users.enumerated()), id: \.element.id) {
                            LobbyRow(offset: $0, user: $1)
                        }
                    }
                }
                .padding(.top, .large)
                .padding(.horizontal, 50)
            }
            .padding(.top, .larger)
            .ignoresSafeArea(.all)
            Spacer()
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
            .padding(.top, .large)
            .padding(.leading, 40)
            .padding(.trailing, 36)
            .ignoresSafeArea(.all)
        }
        .onAppear {
            subscribeToState()
        }
    }
}

// MARK: - Private

extension LobbyView {
    private func subscribeToState() {
        AnyFlow<LobbyContractState>(source: vm.state).collect { state in
            guard let state else { return }
            users = state.users
            roomCode = state.code
            isButtonEnabled = state.isNextButtonEnabled
            buttonTitle = state.buttonText
            loadingState.isLoading = state.isLoading
        } onCompletion: { _ in
        }
    }
}

// MARK: - Previews

struct LobbyView_Previews: PreviewProvider {
    static var previews: some View {
        LobbyView(vm: .init())
            .environmentObject(LoadingState())
            .environmentObject(AlertState())
    }
}
