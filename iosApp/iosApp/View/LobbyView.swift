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

    @State private var users: [UserInLobby] = [
        .init(name: "Dmytro", isGameOwner: true, isReadyToPlay: false),
        .init(name: "Taras", isGameOwner: false, isReadyToPlay: true),
        .init(name: "Artem", isGameOwner: false, isReadyToPlay: true),
        .init(name: "Oleksandr", isGameOwner: false, isReadyToPlay: false),
        .init(name: "Andrii", isGameOwner: false, isReadyToPlay: true),
        .init(name: "Oleh", isGameOwner: false, isReadyToPlay: false),
        .init(name: "Patron", isGameOwner: false, isReadyToPlay: false),
        .init(name: "Jerry", isGameOwner: false, isReadyToPlay: false),
    ]

    // MARK: - Body

    var body: some View {
        ContainerView {
            VStack {
                HStack {
                    VStack(alignment: .leading, spacing: Size.medium.rawValue) {
                        Text("Код для приєднання")
                            .font(.lightBodySecondary)
                        Text("00212314")
                            .font(.titleBoldSecondary)
                    }
                    Spacer()
                    IconButton(.copy) {
                        // TODO: Replace with call to viewmodel
                        alert.isPresentingNoFeature = true
                    }
                    Spacer()
                    IconButton(.share) {
                        // TODO: Replace with call to viewmodel
                        alert.isPresentingNoFeature = true
                    }
                }
                .padding(.medium)
                .overlay(
                    RoundedRectangle(cornerRadius: 4)
                        .stroke(Color.mainBlack, lineWidth: 1)
                )
                .padding(.horizontal, .extraLarge)

                ScrollView(.vertical) {
                    LazyVStack {
                        ForEach($users, id: \.name) {
                            LobbyRow(user: $0)
                        }
                    }
                }
                .padding(.top, .large)
                .padding(.horizontal, 50)
            }
            .padding(.top, 32)
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
                    !users.allSatisfy(\.isReadyToPlay)
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
