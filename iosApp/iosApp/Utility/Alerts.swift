//
//  Alerts.swift
//  iosApp
//
//  Created by Artem Yelizarov on 07.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

final class AlertState: ObservableObject {
    @Published var isPresentingNoFeature = false
    var errorMessage: String? {
        didSet {
            isPresentingError = errorMessage != nil
        }
    }
    @Published var isPresentingError: Bool = false

    private static let proverbs = [
        "Не лізь поперед батька в пекло!",
        "Косо, криво, аби живо!",
        "Не сунься, середа, поперед четверга!",
        "Поперед охоти не лови зайця!",
        "Поспішиш — людей насмішиш!",
    ]
}

// MARK: - Views

extension AlertState {
    var noFeature: Alert {
        Alert(
            title: Text("Йой"),
            message: Text("""
            \(Self.proverbs.randomElement() ?? "")
            (фіча із нот імплементед)"
            """),
            dismissButton: .default(
                Text("Ну ок...")
            )
        )
    }

    var error: Alert {
        Alert(
            title: Text("Йой"),
            message: Text(errorMessage ?? "Щось пішло не так"),
            dismissButton: .default(
                Text("Зрозуміло")
            )
        )
    }
}
