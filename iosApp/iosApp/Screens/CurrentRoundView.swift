//
//  CurrentRoundView.swift
//  iosApp
//
//  Created by Artem Yelizarov on 21.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct CurrentRoundView: View {
    let round: Int

    // MARK: - Body

    var body: some View {
        ContainerView(header: .small) {
            VStack {
                Spacer()
                Text("Раунд \(round)")
                    .font(.largeTitleSemiBold)
                Spacer()
            }
        }
    }
}

// MARK: - Previews

struct CurrentRoundView_Previews: PreviewProvider {
    static var previews: some View {
        CurrentRoundView(round: 1)
    }
}
