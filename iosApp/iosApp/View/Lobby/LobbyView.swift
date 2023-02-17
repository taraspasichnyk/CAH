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

    @State private var users: [Player] = []
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
                PrimaryButton("Готовий") {
                    vm.onNextClicked()
                }
                .disabled(
                    !users.allSatisfy {
                        $0.state == .ready
                    }
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
            DispatchQueue.main.async {
                users = state.users
                roomCode = state.code
                // TODO: Add check for ready button text and availability
            }
        } onCompletion: { _ in
        }
    }
}

// MARK: - Previews

struct LobbyView_Previews: PreviewProvider {
    static var previews: some View {
        LobbyView(vm: .init())
            .environmentObject(AlertState())
    }
}
