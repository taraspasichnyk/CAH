//
//  VoteView.swift
//  iosApp
//
//  Created by Taras Pasichnyk on 01.03.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct VoteView<ViewModel: GameModelProtocol>: View {
    
    // MARK: - Properties

    @ObservedObject var viewModel: ViewModel

    @State var selectedValue: Int = 0

    // MARK: - Lifecycle

    var body: some View {
        ContainerView(header: .small) {
            VStack {
                Text("Раунд \(viewModel.round?.number ?? 0) - Голосування")
                    .padding(.top, .larger)
                    .font(.titleSemiBold)
                Spacer()
                RateView($selectedValue) { newValue in
                    selectedValue = newValue
                }
                .padding(.bottom, .extraLarge)
            }
        }
        .ignoresSafeArea()
    }
}

struct VoteView_Previews: PreviewProvider {
    static var previews: some View {
        VoteView(viewModel: MockGameModel(player: .mock[0]))
    }
}
