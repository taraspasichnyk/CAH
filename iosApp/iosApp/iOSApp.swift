import SwiftUI
import shared

@main
struct iOSApp: App {

    @StateObject private var loading = LoadingState()
    @StateObject private var alert = AlertState()

    init() {
        LoggerKt.doInit()
        KoinIosKt.doInitKoin()
    }

    // MARK: - Body

	var body: some Scene {
		WindowGroup {
			ContentView()
                .environmentObject(loading)
                .environmentObject(alert)
		}
	}
}

final class LoadingState: ObservableObject {
    @Published var isLoading = false
}
