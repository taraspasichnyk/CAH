//
//  SelectorView.swift
//  iosApp
//
//  Created by Taras Pasichnyk on 01.03.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct SelectorView: View {

    private var selectedIndex: Int
    private let count: Int

    init(_ selectedIndex: Int, count: Int) {
        self.selectedIndex = selectedIndex
        self.count = count
    }

    var body: some View {
        HStack {
            ForEach(0..<count, id: \.self) { index in
                Rectangle()
                    .fill(index <= selectedIndex ? Color.mainBlack : Color.accentGrey)
                    .frame(height: 4.0)
                    .cornerRadius(8.0)
            }
        }
    }
}

struct SelectorView_Previews: PreviewProvider {
    static var previews: some View {
        SelectorView(3, count: 9)
            .padding()
    }
}
