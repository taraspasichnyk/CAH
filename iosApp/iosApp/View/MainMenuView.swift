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
        VStack.init(spacing: .large) {
            PrimaryButton("Створити гру") {
                vm.onNewGameSelected()
            }
            PrimaryButton("Приєднатися") {
                vm.onNewGameSelected()
            }
        }
    }
}

// MARK: - Previews

struct MainMenuView_Previews: PreviewProvider {
    static var previews: some View {
        MainMenuView(vm: .init())
    }
}
