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

    @State private var users: [UserInLobby] = []
    @State private var roomCode: String = ""

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
                        // TODO: use id instead
                        ForEach(Array(users.enumerated()), id: \.offset) {
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
                PrimaryButton("Готовий") {
                    vm.onNextClicked()
                }
                .disabled(
                    !users.allSatisfy(\.isReadyToPlay)
                )
            }
            .padding(.top, .large)
            .padding(.leading, 40)
            .padding(.trailing, 36)
            .padding(.bottom, .extraLarge)
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
            // TODO: Add check for ready button text and availability
        } onCompletion: { _ in
        }
    }
}

// MARK: - Previews

struct LobbyView_Previews: PreviewProvider {
    static var previews: some View {
        LobbyView(vm: .init(gameOwner: false))
            .environmentObject(AlertState())
    }
}
