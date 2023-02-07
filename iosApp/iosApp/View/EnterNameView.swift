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

    @State private var name = ""
    @EnvironmentObject private var alert: AlertState
    let vm: GameViewModel

    // MARK: - Body

    var body: some View {
        VStack(alignment: .center) {
            Text("Введіть імʼя")
                .font(.bodyPrimary)
            TextField("Ваше імʼя", text: $name)
            HStack {
                BackButton {
                    alert.isPresentingNoFeature = true
                    // TODO: vm.goBack() or manage state locally, depending on how we choose to do it with shared module
                }
                Spacer()
                PrimaryButton("Далі") {
                    alert.isPresentingNoFeature = true
                }
                .disabled(name.isEmpty)
            }
        }
        .frame(width: 300)
    }
}

// MARK: - Previews

struct EnterNameView_Previews: PreviewProvider {
    static var previews: some View {
        EnterNameView(vm: .init())
    }
}
