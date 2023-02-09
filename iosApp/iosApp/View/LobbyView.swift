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
    ]

    var body: some View {
        ContainerView {
            HStack {
                VStack {
                    Text("Код для приєднання")
                    Text("00212314")
                }
                Image(systemName: "copy")
                Image(systemName: "share")
            }
            List {
                ForEach(users, id: \.name) {
                    Text($0.name)
                }
            }
            .listStyle(.plain)
            HStack {
                BackButton {
                    _ = navState.popLast()
                }
                Spacer()
                PrimaryButton("Розпочати гру") {
                    alert.isPresentingNoFeature = true
                }
                .disabled(
                    !users.allSatisfy(\.isReadyToPlay)
                )
            }
        }
    }
}

struct LobbyView_Previews: PreviewProvider {
    static var previews: some View {
        LobbyView(navState: .constant([]))
            .environmentObject(AlertState())
    }
}
