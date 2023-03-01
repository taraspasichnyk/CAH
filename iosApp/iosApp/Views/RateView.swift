//
//  RateView.swift
//  iosApp
//
//  Created by Taras Pasichnyk on 01.03.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct RateView: View {
    var body: some View {
        VStack {
            Spacer()
        }
        .aspectRatio(124 / 168, contentMode: .fit)
        .background(
            LinearGradient(
                colors: [
                    Color(red: 0.94, green: 0.94, blue: 0.94),
                    Color.white,
                    Color(red: 0.92, green: 0.92, blue: 0.92),
                ],
                startPoint: .topLeading,
                endPoint: .bottomTrailing
            )
        )
        .compositingGroup()
        .cornerRadius(8.0)
        .shadow(radius: 8.0, y: 4.0)
    }
}

struct RateView_Previews: PreviewProvider {
    static var previews: some View {
        RateView()
            .frame(width: 328.0, height: 137.0)
    }
}
