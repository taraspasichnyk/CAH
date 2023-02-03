import SwiftUI
import shared

@main
struct iOSApp: App {

    @State var state: GameState = shared.GameState.InMenu()
    var vm = shared.GameViewModel()

    init() {
        shared.LoggerKt.doInit()
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
		}
	}
}

// MARK: - Private

extension iOSApp {
    private func subscribeToState() {
        vm.state.collect { state in
            guard let state else { return }
            self.state = state
        } onCompletion: { _ in
        }
    }
}
