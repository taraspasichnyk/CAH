//
//  HeaderView.swift
//  iosApp
//
//  Created by Artem Yelizarov on 07.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct HeaderView: View {

    // MARK: - Body

    var body: some View {
        ZStack {
            Image.bgTexture
                .padding(.bottom, 44)
            VStack {
                Spacer()
                Image.logo
                    .resizable()
                    .scaledToFit()
                    .frame(width: 238)
            }
        }
        .ignoresSafeArea()
        .frame(height: 224)
    }
}

// MARK: - Previews

struct HeaderView_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            HeaderView()
            Spacer()
        }
    }
}
