//
//  ContainerView.swift
//  iosApp
//
//  Created by Artem Yelizarov on 07.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct ContainerView<Content: View>: View {
    let content: Content

    init(@ViewBuilder _ content: () -> Content) {
        self.content = content()
    }

    // MARK: - Body

    var body: some View {
        ZStack {
            VStack {
                Spacer()
                Image.bgTexture
                    .frame(height: .extraLarge)
            }
            .ignoresSafeArea(.all)
            VStack {
                HeaderView()
                    .fixedSize(horizontal: false, vertical: true)
                    .ignoresSafeArea(.all)
                content
            }
            .ignoresSafeArea(.all, edges: .top)
        }
        .navigationBarBackButtonHidden(true)
    }
}

// MARK: - Previews

struct ContainerView_Previews: PreviewProvider {
    static var previews: some View {
        ContainerView {
            Spacer()
        }
    }
}
