//
//  EnterNameView.swift
//  iosApp
//
//  Created by Artem Yelizarov on 03.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct EnterNameView: View {

    @State var name = ""
    let vm: GameViewModel

    // MARK: - Body

    var body: some View {
        Text("Введіть імʼя")
        TextField("Ваше імʼя", text: $name)
        HStack {
            BackButton {
                // TODO: vm.goBack() or manage state locally, depending on how we choose to do it with shared module
            }
        }
    }
}

// MARK: - Previews

struct EnterNameView_Previews: PreviewProvider {
    static var previews: some View {
        EnterNameView(vm: .init())
    }
}
