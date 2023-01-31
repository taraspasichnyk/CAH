import SwiftUI
import shared

@main
struct iOSApp: App {
    @State var state: GameState = shared.GameState.InMenu()
    var vm = shared.GameViewModel()

	var body: some Scene {
		WindowGroup {
			ContentView(
                state: $state,
                vm: vm
            )
            .onAppear {
                vm.observeState().collect { state in
                    guard let state else { return }
                    self.state = state
                } onCompletion: { _ in

                }
            }
		}
	}
}
