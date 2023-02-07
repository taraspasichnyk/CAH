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
}

// MARK: - Views

extension AlertState {
    var noFeature: Alert {
        Alert(
            title: Text("Йой"),
            message: Text("Не лізь поперед батька в пекло!\n(фіча із нот імплементед)"),
            dismissButton: .default(
                Text("Ну ок...")
            )
        )
    }
}
