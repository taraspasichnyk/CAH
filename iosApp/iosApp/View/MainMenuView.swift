//
//  MainMenuView.swift
//  iosApp
//
//  Created by Artem Yelizarov on 03.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct MainMenuView: View {
    var vm: MenuViewModel

    // MARK: - Body
    
    var body: some View {
        VStack {
            ZStack {
                Image.bgTexture
                Image.logo
                    .resizable()
                    .scaledToFit()
                    .padding(.horizontal, 52)
            }
            Spacer()
                .frame(height: 64)
            VStack.init(spacing: .large) {
                PrimaryButton("Створити гру") {
                    vm.onNewGameSelected()
                }
                PrimaryButton("Приєднатися") {
                    vm.onJoinGameSelected()
                }
            }
            Spacer()
                .frame(height: 282)
            Image.bgTexture
                .frame(height: 44)
        }
        .ignoresSafeArea()
    }
}

// MARK: - Previews

struct MainMenuView_Previews: PreviewProvider {
    static var previews: some View {
        MainMenuView(vm: .init())
    }
}
