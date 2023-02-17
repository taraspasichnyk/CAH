import SwiftUI
import shared

@main
struct iOSApp: App {

    @StateObject private var alert = AlertState()

    init() {
        LoggerKt.doInit()
        KoinIosKt.doInitKoin()
    }

    // MARK: - Body

	var body: some Scene {
		WindowGroup {
			ContentView()
                .environmentObject(alert)
		}
	}
}
