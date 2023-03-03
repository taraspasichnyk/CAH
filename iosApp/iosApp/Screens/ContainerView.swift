//
//  ContainerView.swift
//  iosApp
//
//  Created by Artem Yelizarov on 07.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct ContainerView<Content: View>: View {
    private let headerSize: HeaderSize
    private let content: Content

    init(
        header: HeaderSize = .medium,
        @ViewBuilder _ content: () -> Content
    ) {
        self.headerSize = header
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
                HeaderView(size: headerSize)
                    .fixedSize(horizontal: false, vertical: true)
                content
                Spacer()
                    .frame(height: .extraLarge)
            }
        }
        .navigationBarBackButtonHidden(true)
    }
}

// MARK: - Previews

struct ContainerView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            ContainerView(header: .small) {
                Spacer()
            }
            ContainerView(header: .medium) {
                Spacer()
            }
        }
    }
}
