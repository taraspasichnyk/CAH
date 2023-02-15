import SwiftUI
import shared

@main
struct iOSApp: App {

    @StateObject private var alert = AlertState()

    init() {
        LoggerKt.doInit()
        KoinIosKt.doInitKoin()

        // TODO: If possible, move it out into shared
        // Make sure anonymous login is called before using Firebase
        Injector.shared.anonymousLogin.invoke { error in
            guard let error else { return }
            // TODO: Maybe check for connectivity errors, otherwise do nothing or enter error state
            print(error)
        }
    }

    // MARK: - Body

	var body: some Scene {
		WindowGroup {
			ContentView()
                .environmentObject(alert)
		}
	}
}
