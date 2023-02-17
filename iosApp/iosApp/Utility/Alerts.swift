//
//  Alerts.swift
//  iosApp
//
//  Created by Artem Yelizarov on 07.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

final class AlertState: ObservableObject {

    @Published var isPresentingAlert: Bool = false

    var presentedAlertType: AlertType? {
        didSet {
            isPresentingAlert = presentedAlertType != nil
        }
    }

    private static let proverbs = [
        "Не лізь поперед батька в пекло!",
        "Косо, криво, аби живо!",
        "Не сунься, середа, поперед четверга!",
        "Поперед охоти не лови зайця!",
        "Поспішиш — людей насмішиш!",
    ]
}

// MARK: - AlertType

extension AlertState {
    enum AlertType {
        case noFeature
        case error(message: String)
    }
}

// MARK: - Views

extension AlertState {
    var alert: Alert {
        Alert(
            title: Text("Йой"),
            message: Text(alertMessage),
            dismissButton: .default(
                Text("Зрозуміло")
            ) { [weak self] in
                self?.presentedAlertType = nil
            }
        )
    }

    private var alertMessage: String {
        switch presentedAlertType {
        case .error(message: let message):
            return message
        case .noFeature:
            return """
                   \(Self.proverbs.randomElement() ?? "")
                   (фіча із нот імплементед)"
                   """
        case .none:
            return "Щось пішло не так"
        }
    }
}
