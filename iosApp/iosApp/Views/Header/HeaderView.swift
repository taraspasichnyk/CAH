//
//  HeaderView.swift
//  iosApp
//
//  Created by Artem Yelizarov on 07.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct HeaderView: View {
    let size: HeaderSize

    // MARK: - Body

    var body: some View {
        ZStack {
            Image.bgTexture
                .padding(.bottom, size.headerBackgroundBottomPadding)
            VStack {
                Spacer()
                Image.logo
                    .resizable()
                    .scaledToFit()
                    .frame(width: size.logoWidth)
            }
        }
        .ignoresSafeArea()
        .frame(height: size.height)
    }
}

// MARK: - Previews

struct HeaderView_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            HeaderView(size: .medium)
            Spacer()
        }
    }
}
