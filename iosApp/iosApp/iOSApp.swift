import SwiftUI
import shared

@main
struct iOSApp: App {

    var vm = shared.GameViewModel()

    init() {
        shared.LoggerKt.doInit()
    }

    @State var state: GameState = shared.GameState.InMenu()




	var body: some Scene {
		WindowGroup {
			ContentView(
                state: $state,
                vm: vm
            )
            .onAppear {
                vm.state.collect { state in
                    guard let state else { return }
                    self.state = state
                } onCompletion: { _ in
                }
            }
		}
	}
}
