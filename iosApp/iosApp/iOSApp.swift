import SwiftUI
import shared

@main
struct iOSApp: App {

    @State private var state: GameContractGameState = GameContractGameState.InMenu()
    @StateObject private var alert = AlertState()
    var vm: GameViewModel!

    init() {
        LoggerKt.doInit()
        KoinIosKt.doInitKoin()

        vm = Injector.shared.gameViewModel
    }

    // MARK: - Body

	var body: some Scene {
		WindowGroup {
			ContentView(
                state: $state,
                vm: vm
            )
            .onAppear {
                subscribeToState()
            }
            .environmentObject(alert)
		}
	}
}

// MARK: - Private

extension iOSApp {
    private func subscribeToState() {
        AnyFlow<GameContractGameState>(source: vm.state).collect { state in
            guard let state else { return }
            self.state = state
        } onCompletion: { _ in
        }
    }
}
