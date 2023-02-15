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
    @Binding var navState: [NavigationState]
    @EnvironmentObject private var alert: AlertState

    @State private var users: [Player] = [
        .init(id: "a0", nickname: "Dmytro", gameOwner: true, cards: [], score: 0, state: .notReady),
        .init(id: "b1", nickname: "Taras", gameOwner: false, cards: [], score: 0, state: .ready),
        .init(id: "c2", nickname: "Artem", gameOwner: false, cards: [], score: 0, state: .ready),
        .init(id: "d3", nickname: "Oleksandr", gameOwner: false, cards: [], score: 0, state: .notReady),
        .init(id: "e4", nickname: "Andrii", gameOwner: false, cards: [], score: 0, state: .ready),
        .init(id: "f5", nickname: "Oleh", gameOwner: false, cards: [], score: 0, state: .notReady),
        .init(id: "g6", nickname: "Patron", gameOwner: false, cards: [], score: 0, state: .notReady),
        .init(id: "h7", nickname: "Jerry", gameOwner: false, cards: [], score: 0, state: .notReady),
    ]

    private let shareController: PasteboardControlling
    private let roomCode = "00212314" // TODO: Get actual code from state

    init(
        navState: Binding<[NavigationState]>,
        shareController: PasteboardControlling = PasteboardController.shared
    ) {
        self._navState = navState
        self.shareController = shareController
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
                        shareController.copyToPasteboard(roomCode)
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
                        ForEach($users, id: \.nickname) {
                            LobbyRow(user: $0)
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
                    // TODO: Replace with call to viewmodel
                    _ = navState.popLast()
                }
                Spacer()
                PrimaryButton("Готовий") {
                    // TODO: Replace with call to viewmodel
                    alert.isPresentingNoFeature = true
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
    }
}

// MARK: - Previews

struct LobbyView_Previews: PreviewProvider {
    static var previews: some View {
        LobbyView(navState: .constant([]))
            .environmentObject(AlertState())
    }
}
