//
//  PasteboardController.swift
//  iosApp
//
//  Created by Artem Yelizarov on 11.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import UIKit
import UniformTypeIdentifiers

protocol PasteboardControlling {
    func copyToPasteboard(_ text: String)
}

final class PasteboardController: PasteboardControlling {
    static let shared = PasteboardController()
    private init() {}

    func copyToPasteboard(_ text: String) {
        UIPasteboard.general.setValue(
            text,
            forPasteboardType: UTType.plainText.identifier
        )
    }
}
