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
    var vm: GameViewModel

    // MARK: - Body
    
    var body: some View {
        VStack{
            Button("Створити гру") {
                vm.onNewGameClick()
            }
            Button("Приєднатися") {
                vm.onNewGameClick()
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
