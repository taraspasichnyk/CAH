import SwiftUI
import shared

@main
struct iOSApp: App {

    var vm: GameViewModel!

    init() {
        LoggerKt.doInit()
        KoinIosKt.doInitKoin()

        vm = Injector.shared.gameViewModel as! GameViewModel
    }

    @State var state: GameContractGameState = GameContractGameState.InMenu()


	var body: some Scene {
		WindowGroup {
			ContentView(
                state: $state,
                vm: vm
            )
            .onAppear {
                AnyFlow<GameContractGameState>(source: vm.state).collect { state in
                    guard let state else { return }
                    self.state = state
                } onCompletion: { _ in
                }
            }
		}
	}
}
